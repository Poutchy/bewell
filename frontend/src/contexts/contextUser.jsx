import React from "react";

const initialState = {
    userId: undefined,
    setUserId: valueId => {},
    userToken: "",
    setUserToken: valueToken => {},
};

const ContextUser = React.createContext(initialState);

export { initialState, ContextUser }