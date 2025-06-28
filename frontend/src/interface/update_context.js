import { useContext } from "react";
import { ContextUser, initialState } from "../contexts/contextUser";

const useUpdateContext = () => {
    const { setUserId, setUserToken, setUserRole } = useContext(ContextUser);
    async function upContext(userData){
        setUserId(userData.id);
        setUserToken(userData.token);
        setUserRole(userData.role);
    }
    return upContext;
}

const useResetContext = () => {
    const { setUserId, setUserToken, setUserRole } = useContext(ContextUser);
    function resetContext(){
        window.sessionStorage.setItem("userId", initialState.userId);
        window.sessionStorage.setItem("userToken", initialState.userToken);
        window.sessionStorage.setItem("userRole", initialState.userRole);
        setUserId(initialState.userId);
        setUserToken(initialState.userToken);
        setUserRole(initialState.userRole);
    }
    return resetContext;
}

export { useUpdateContext, useResetContext }