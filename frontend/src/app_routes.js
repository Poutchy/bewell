import {Route, Routes} from "react-router-dom";
import {HomePage, NotFound, Reservation, SalonVisualisationPage} from "./page";
import {ProtectedRoutes} from "./components/protected_route";
import {useIsConnected} from "./interface";

export function AppRoutes() {
    const isConnected = useIsConnected();

    return (
        <Routes>
            <Route path="/" element={<HomePage/>}/>
            <Route path="/salons" element={<SalonVisualisationPage/>}/>
            <Route element={ <ProtectedRoutes isAllowed={isConnected()} to={"/authentication"} /> }>
                <Route path="/reservation/:id" element={<Reservation/>} />
            </Route>
            <Route element={ <ProtectedRoutes isAllowed={!(isConnected())} to={"/"} /> }>
                <Route path="/authentication" element={<Authentication />} />
                <Route path="/connection" element={<Connection />} />
                <Route path="/registration" element={<Registration />} />
            </Route>
            <Route path="*" element={<NotFound/>}/>
        </Routes>
    )
}