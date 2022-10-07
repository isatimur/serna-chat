import React from "react";
import "./Nav.css";
import logo from "../../images/img.png"

const Nav = props => {
    return (
        <div className="nav">
            <div className="nav__blocks">
                <img width="50px" height="50px" src={logo} alt=""/>
            </div>
            <div className="nav__blocks"></div>
            <div className="nav__blocks"></div>
        </div>
    );
}
export default Nav