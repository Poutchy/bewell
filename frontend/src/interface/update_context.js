import { useContext } from "react";
import { ContextUser, initialState } from "../contexts/contextUser";

const useUpdateContext = () => {
    const { setUserToken, setUserRole } = useContext(ContextUser);

    async function upContext(userData) {
        setUserToken(userData.token);
        setUserRole(userData.role);
        // Return a resolved promise to allow chaining
        return Promise.resolve();
    }

    return upContext;
};


const useResetContext = () => {
    const { setUserToken, setUserRole } = useContext(ContextUser);
    function resetContext(){
        window.sessionStorage.setItem("userToken", initialState.userToken);
        window.sessionStorage.setItem("userRole", initialState.userRole);
        setUserToken(initialState.userToken);
        setUserRole(initialState.userRole);
    }
    return resetContext;
}

export { useUpdateContext, useResetContext }