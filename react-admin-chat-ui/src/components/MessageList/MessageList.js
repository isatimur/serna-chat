import React, {useState, createRef, useEffect} from "react";
import "./MessageList.css"
import Avatar from "../ChatFeed/Avatar";
import MessageItem from "./MessageItem";


let messagesEndRef = createRef(null);
const initalialArray  =[ {
    "messageId": 1,
    "image": "https://i.pravatar.cc/150?img=14",
    "type": "",
    "msg": "Hi Tim, How are you?",
    "seen": true,
    "messageDate":"2012-04-21T18:25:43-05:00"
},
    {
        "messageId": 2,
        "image": "https://i.pravatar.cc/150?img=15",
        "type": "other",
        "msg": "I am fine.",
        "seen": true,
        "messageDate":"2012-04-21T18:25:43-05:00"
    },
    {
        "messageId": 3,
        "image": "https://i.pravatar.cc/150?img=15",
        "type": "other",
        "msg": "What about you?",
        "seen": true,
        "messageDate":"2012-04-21T18:25:43-05:00"
    },
    {
        "messageId": 4,
        "image": "https://i.pravatar.cc/150?img=14",
        "type": "",
        "seen": true,
        "messageDate":"2012-04-21T18:25:43-05:00",
        "msg": "Awesome these days."
    },
    {
        "messageId": 5,
        "image": "https://i.pravatar.cc/150?img=15",
        "type": "other",
        "seen": true,
        "messageDate":"2012-04-21T18:25:43-05:00",
        "msg": "Finally. What's the plan?"
    },
    {
        "messageId": 6,
        "image": "https://i.pravatar.cc/150?img=14",
        "type": "",
        "seen": true,
        "messageDate":"2012-04-21T18:25:43-05:00",
        "msg": "what plan mate?"
    },
    {
        "messageId": 7,
        "chatId": "356c3278-3eb3-11ed-b878-0242ac120002",
        "image": "https://i.pravatar.cc/150?img=15",
        "type": "other",
        "msg": "I'm talking about the tutorial",
        "messageDate":"2012-04-21T18:25:43-05:00",
        "seen": false
    }]
const MessageList = () => {
    const [chat, setChat] = useState(initalialArray);
    const [message, setMessage] = useState("");
    const scrollToBottom = () => {
        messagesEndRef.current.scrollIntoView({behavior: "smooth"});
    };

    useEffect(() => {

        const listener = (e) => {
            if (e.key === 'Enter') {
                e.preventDefault()
                e.stopPropagation()
                if (message !== "") {
                    initalialArray.push(
                        {
                            "messageId": 1,
                            "chatId": "356c3278-3eb3-11ed-b878-0242ac120002",
                            "image": "https://i.pravatar.cc/150?img=15",
                            "type": "",
                            "msg": message,
                            "messageDate": "2022-09-28T18:55:48.893",
                            "seen": false
                        })
                    setChat([...initalialArray])
                    scrollToBottom()
                    setMessage("")
                }
            }
        }
        scrollToBottom()
        window.addEventListener("keydown", listener)
        return () => {
            window.removeEventListener("keydown", listener)
        }
    }, [message])

    return (<div className="main__messagelist">
            <div className="messagelist__header">
                <div className="blocks">
                    <div className="current-chatting-user">
                        <Avatar
                            isOnline="active"
                            image="https://i.pravatar.cc/150?img=15"
                        />
                        <p>Tim Hover</p>
                    </div>
                </div>

                <div className="blocks">
                    <div className="settings">
                        <button className="btn-nobg">
                            <i className="fa fa-cog"></i>
                        </button>
                    </div>
                </div>
            </div>
            <div className="messagelist__body">
                <div className="chat__items">
                    {chat.map((item, index) => {
                        return (
                            <MessageItem
                                animationDelay={index + 2}
                                key={index}
                                user={item.type ? item.type : "me"}
                                msg={item.msg}
                                image={item.image}
                            />
                        );
                    })}
                    <div ref={messagesEndRef}/>
                </div>
            </div>
            <div className="messagelist__footer">
                <div className="sendNewMessage">
                    <button className="addFiles">
                        <i className="fa fa-plus"></i>
                    </button>
                    <input
                        type="text"
                        placeholder="Type a message here"
                        onChange={event => setMessage(event.target.value)}
                        value={message}
                    />
                    <button className="btnSendMsg" id="sendMsgBtn">
                        <i className="fa fa-paper-plane"></i>
                    </button>
                </div>
            </div>
        </div>
    )
}
export default MessageList