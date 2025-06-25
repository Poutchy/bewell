import { useContext } from "react";
import { ContextUser, initialState } from "../contexts/contextUser"

const useIsConnected = () => {
    const { userToken } = useContext(ContextUser);
    function isConn(){
        return userToken !== initialState.userToken;
    }
    return isConn;
}

export { useIsConnected }