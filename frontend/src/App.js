import React, {useEffect, useState} from "react";
import './App.css';
import {AppRoutes} from "./app_routes";
import {ContextUser, initialState} from "./contexts/contextUser";
import {Header} from "./components";
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns';

function App() {
    const [ userToken, setUserToken ] = useState(initialState.userToken);
    const [ userRole, setUserRole ] = useState(initialState.userRole);

    // default state for the session
    useEffect(() => {
        if(window.sessionStorage.getItem("userRole") === null){
            window.sessionStorage.setItem("userId", initialState.userRole);
        }
        if(window.sessionStorage.getItem("userToken") === null){
            window.sessionStorage.setItem("userToken", initialState.userToken);
        }
    }, [])

    // update the session with the data from the context if the context isn't undefined otherwise update the contexte with the data from the session
    useEffect(() => {
        if(userRole !== initialState.userRole){
            window.sessionStorage.setItem("userRole", userRole);
        }else{
            setUserRole(window.sessionStorage.getItem("userRole"));
        }
    }, [userRole]);

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
        <LocalizationProvider dateAdapter={AdapterDateFns}>
            <ContextUser.Provider value={{ userToken, setUserToken, userRole, setUserRole }}>
                <header>
                    <Header/>
                </header>
                <main>
                    <AppRoutes />
                </main>
                <footer style={{zIndex:300}}>
                </footer>
            </ContextUser.Provider>
      </LocalizationProvider>
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
