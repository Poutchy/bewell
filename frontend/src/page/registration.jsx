import React from "react";
import { useLocation } from "react-router-dom";
import { GoBack, RegistrationForm } from "../components";
import "../css/auth.css";

export function Registration() {
    // const { state } = useLocation();

    return(<div className="main">
        <h1 className="form_title">Registration</h1>
        <GoBack />
    </div>)
}

//<RegistrationForm mail={state.mail}/>
