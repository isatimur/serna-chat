import React from "react";
import "./ChatBody.css"
import ChatFeed from "../ChatFeed/ChatFeed";
import MessageList from "../MessageList/MessageList";
import UserProfile from "../UserProfile/UserProfile";
const ChatBody = props => {
    return (
        <div className="main__chatbody">
            <ChatFeed></ChatFeed>
            <MessageList></MessageList>
            <UserProfile></UserProfile>
        </div>
    )
}
export default ChatBody