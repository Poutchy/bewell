import React, {useEffect, useState} from "react";
import './App.css';
import {AppRoutes} from "./app_routes";
import {ContextUser, initialState} from "./contexts/contextUser";
import {Header} from "./components";


function App() {
    const [ userId, setUserId ] = useState(initialState.userId);
    const [ userToken, setUserToken ] = useState(initialState.userToken);

    // default state for the session
    useEffect(() => {
        if(window.sessionStorage.getItem("userId") === null){
            window.sessionStorage.setItem("userId", initialState.userId);
        }
        if(window.sessionStorage.getItem("userToken") === null){
            window.sessionStorage.setItem("userToken", initialState.userToken);
        }
    }, [])

    // update the session with the data from the context if the context isn't undefined otherwise update the contexte with the data from the session
    useEffect(() => {
        if(userId !== initialState.userId){
            window.sessionStorage.setItem("userId", userId);
        }else{
            setUserId(window.sessionStorage.getItem("userId"));
        }
    }, [userId]);

    useEffect(() => {
        if(userToken !== initialState.userToken){
            window.sessionStorage.setItem("userToken", userToken);
        }else{
            setUserToken(window.sessionStorage.getItem("userToken"));
        }
    }, [userToken, setUserToken]);

    // return (
    //     <ContextUser.Provider value={{ userId, setUserId, userToken, setUserToken }}>
    //         <header>
    //             <Header/>
    //         </header>
    //         <main>
    //             <AppRoutes />
    //         </main>
    //         <footer style={{zIndex:300}}>
    //             <Footer/>
    //         </footer>
    //     </ContextUser.Provider>
    // )
    return (
        <ContextUser.Provider value={{ userId, setUserId, userToken, setUserToken }}>
            <header>
                <Header/>
            </header>
            <main>
                <AppRoutes />
            </main>
            <footer style={{zIndex:300}}>
            </footer>
        </ContextUser.Provider>
    )
}

// function App() {
//   return (
//     <div className="App">
//       <AppRoutes/>
//     </div>
//   );
// }

export default App;
