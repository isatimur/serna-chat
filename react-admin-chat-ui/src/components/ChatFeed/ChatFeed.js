import React, {useEffect, useState} from "react";
import "./ChatFeed.css"
import ChatFeedItems from "./ChatFeedItems";
import axios from "axios";
import {over} from 'stompjs';
import SockJS from 'sockjs-client';

var stompClient = null;
const allChatsPrepared = [
    {
        "id": "356c3278-3eb3-11ed-b878-0242ac120002",
        "image": "https://i.pravatar.cc/150?img=15",
        "name": "Tim Hover",
        "active": true,
        "online": true
    },
    {
        "id": "356c37f0-3eb3-11ed-b878-0242ac120002",
        "image": "https://i.pravatar.cc/150?img=2",
        "name": "Ayub Rossi",
        "active": false,
        "online": false
    },
    {
        "id": "356c3980-3eb3-11ed-b878-0242ac120002",
        "image": "https://i.pravatar.cc/150?img=3",
        "name": "Hamaad Dejesus",
        "active": false,
        "online": false
    },
    {
        "id": "356c3d68-3eb3-11ed-b878-0242ac120002",
        "image": "https://i.pravatar.cc/150?img=4",
        "name": "Eleni Hobbs",
        "active": false,
        "online": true
    },
    {
        "id": "356c3e80-3eb3-11ed-b878-0242ac120002",
        "image": "https://i.pravatar.cc/150?img=5",
        "name": "Elsa Black",
        "active": false,
        "online": false
    },
    {
        "id": "356c3fa2-3eb3-11ed-b878-0242ac120002",
        "image": "https://i.pravatar.cc/150?img=6",
        "name": "Kayley Mellor",
        "active": false,
        "online": true
    },
    {
        "id": "356c40ba-3eb3-11ed-b878-0242ac120002",
        "image": "https://i.pravatar.cc/150?img=7",
        "name": "Hasan Mcculloch",
        "active": false,
        "online": true
    },
    {
        "id": "356c41be-3eb3-11ed-b878-0242ac120002",
        "image": "https://i.pravatar.cc/150?img=8",
        "name": "Autumn Mckee",
        "active": false,
        "online": false
    },
    {
        "id": "356c42e0-3eb3-11ed-b878-0242ac120002",
        "image": "https://i.pravatar.cc/150?img=9",
        "name": "Allen Woodley",
        "active": false,
        "online": true
    },
    {
        "id": "356c43f8-3eb3-11ed-b878-0242ac120002",
        "image": "https://i.pravatar.cc/150?img=10",
        "name": "Manpreet David",
        "active": false,
        "online": true
    }
]

const ChatFeed = props => {
    const [privateChats, setPrivateChats] = useState(new Map());
    const [publicChats, setPublicChats] = useState([]);
    const [tab, setTab] = useState("CHATROOM");
    const [userData, setUserData] = useState({
        username: '',
        receivername: '',
        connected: false,
        message: ''
    });
    const [allChats, setAllChats] = useState(allChatsPrepared);
    const [error, setError] = useState(null);
    useEffect(() => {
        console.log(userData);
        axios("http://localhost:8080/v1/api/chats")
            .then((response) => {
                setAllChats(response.data);
                setError(null);
            })
            .catch(setError);

    }, [userData]);

    const connect = () => {
        let Sock = new SockJS('http://localhost:8080/ws/chat');
        stompClient = over(Sock);
        stompClient.connect({}, onConnected, onError);
    }

    const onConnected = () => {
        setUserData({...userData, "connected": true});
        stompClient.subscribe('/chatroom/public', onMessageReceived);
        stompClient.subscribe('/user/' + userData.username + '/private', onPrivateMessage);
        userJoin();
    }

    const userJoin = () => {
        var chatMessage = {
            senderName: userData.username,
            status: "JOIN"
        };
        stompClient.send("/app/message", {}, JSON.stringify(chatMessage));
    }

    const onMessageReceived = (payload) => {
        var payloadData = JSON.parse(payload.body);
        switch (payloadData.status) {
            case "JOIN":
                if (!privateChats.get(payloadData.senderName)) {
                    privateChats.set(payloadData.senderName, []);
                    setPrivateChats(new Map(privateChats));
                }
                break;
            case "MESSAGE":
                publicChats.push(payloadData);
                setPublicChats([...publicChats]);
                break;
        }
    }
    const onPrivateMessage = (payload) => {
        console.log(payload);
        var payloadData = JSON.parse(payload.body);
        if (privateChats.get(payloadData.senderName)) {
            privateChats.get(payloadData.senderName).push(payloadData);
            setPrivateChats(new Map(privateChats));
        } else {
            let list = [];
            list.push(payloadData);
            privateChats.set(payloadData.senderName, list);
            setPrivateChats(new Map(privateChats));
        }
    }

    const onError = (err) => {
        console.log(err);

    }

    const handleMessage = (event) => {
        const {value} = event.target;
        setUserData({...userData, "message": value});
    }
    const sendValue = () => {
        if (stompClient) {
            var chatMessage = {
                senderName: userData.username,
                message: userData.message,
                status: "MESSAGE"
            };
            console.log(chatMessage);
            stompClient.send("/app/message", {}, JSON.stringify(chatMessage));
            setUserData({...userData, "message": ""});
        }
    }
    const sendPrivateValue = () => {
        if (stompClient) {
            var chatMessage = {
                senderName: userData.username,
                receiverName: tab,
                message: userData.message,
                status: "MESSAGE"
            };

            if (userData.username !== tab) {
                privateChats.get(tab).push(chatMessage);
                setPrivateChats(new Map(privateChats));
            }
            stompClient.send("/app/private-message", {}, JSON.stringify(chatMessage));
            setUserData({...userData, "message": ""});
        }
    }

    const handleUsername = (event) => {
        const {value} = event.target;
        setUserData({...userData, "username": value});
    }

    const registerUser = () => {
        connect();
    }
    const handleClick = (e) => {
        alert("You have clicked mock!")
    }

    return (
        <div className="main__chatfeed">
            <button className="btn" onClick={(e) => handleClick(e)}>
                <i className="fa fa-plus"></i>
                <span>New conversation</span>
            </button>
            <div className="chatfeed__heading">
                <h2>Chats</h2>
                <button className="btn-nobg">
                    <i className="fa fa-ellipsis-h"></i>
                </button>
            </div>
            <div className="chatfeed__search">
                <div className="search_wrap">
                    <input type="text" placeholder="Search Here" required/>
                    <button className="search-btn">
                        <i className="fa fa-search"></i>
                    </button>

                </div>
            </div>
            <div className="chatfeed__items">
                {allChats.map((item, index) => {
                    return (
                        <ChatFeedItems
                            key={index}
                            image={item.image}
                            name={item.name}
                            animationDelay={index + 1}
                            isOnline={item.online ? "active" : ""}
                            active={item.active ? "active" : ""}
                        />
                    )
                })}
            </div>
        </div>

    )
}
export default ChatFeed