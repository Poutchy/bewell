import axios from "axios";
import React from "react";

const initialState = {

    userToken: "",
    setUserToken: valueToken => {},
    userRole: undefined,
    setUserRole: valueRole => {},
};

const ContextUser = React.createContext(initialState);

export { initialState, ContextUser }

/*
    userId: undefined,
    setUserId: valueId => {},
 */