import React from "react";
import { Link } from "react-router-dom";
import { HomeButton } from "../components";
import "../css/auth.css";
import {AppProvider, SignInPage} from "@toolpad/core";

export function Authentication() {
    const providers = [
        { id: 'google', name: 'Google' },
        { id: 'credentials', name: 'Email and Password' }
    ];

    const signIn = async (
        provider,
    ) => {
        const promise = new Promise((resolve) => {
            setTimeout(() => {
                console.log(`Sign in with ${provider.id}`);
                resolve({ error: 'This is a mock error message.' });
            }, 500);
        });
        return promise;
    };

    return(<div className="main">
        <h1 className="form_title">Authentification</h1>
        <AppProvider>
            <SignInPage
                signIn={signIn}
                providers={providers}
                slotProps={{ emailField: { autoFocus: false }, form: { noValidate: true } }}
            />
        </AppProvider>
        <Link to="/authentication">
            Sign up
        </Link>
        <HomeButton />
    </div>)
}
