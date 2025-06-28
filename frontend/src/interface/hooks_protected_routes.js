import { useContext } from "react";
import { ContextUser, initialState } from "../contexts/contextUser"

const useIsConnected = () => {
    const { userToken } = useContext(ContextUser);
    function isConn(){
        return userToken !== initialState.userToken;
    }
    return isConn;
}

const useIsOK = () => {
    const { userRole } = useContext(ContextUser);
    function isConn(){
        return userRole === "Client";
    }
    return isConn();
}

export { useIsConnected, useIsOK }