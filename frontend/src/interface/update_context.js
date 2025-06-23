import { useContext } from "react";
import { ContextUser, initialState } from "../contexts/context_user";

const useUpdateContext = () => {
    const { setUserId, setUserToken } = useContext(ContextUser);
    async function upContext(userData){
        setUserId(userData.id);
        setUserToken(userData.token);
    }
    return upContext;
}

const useResetContext = () => {
    const { setUserId, setUserToken } = useContext(ContextUser);
    function resetContext(){
        window.sessionStorage.setItem("userId", initialState.userId);
        window.sessionStorage.setItem("userToken", initialState.userToken);
        setUserId(initialState.userId);
        setUserToken(initialState.userToken);
    }
    return resetContext;
}

export { useUpdateContext, useResetContext }