import React from "react";
import { useLocation } from "react-router-dom";
import { ConnectionForm, GoBack } from "../components";
import "../css/auth.css";

export function Connection() {
    // const { state } = useLocation();

    return(<div className="main">
        <h1 className="form_title">Connexion</h1>
        <GoBack />
    </div>)
}

//         <ConnectionForm mail={state.mail}/>