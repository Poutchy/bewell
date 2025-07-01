import {Route, Routes} from "react-router-dom";
import {HomePage, CreateReservation, NotFound, Reservation, Salon, Salons, Reviews, Review, Authentication} from "./page";
import {ProtectedRoutes} from "./components";
import {useIsConnected} from "./interface";

export function AppRoutes() {
    const isConnected = useIsConnected();

    return (
        <Routes>
            <Route path="/" element={<HomePage/>}/>
            <Route path="/salons" element={<Salons/>}/>
            <Route path="/salon/:id" element={<Salon/>}/>
            <Route path="/reviews" element={<Reviews/>} />
            <Route path="/review/:id" element={<Review/>} />
            <Route element={ <ProtectedRoutes isAllowed={isConnected()} to={"/authentication"} /> }>
                <Route path="/reservation" element={<CreateReservation />} />
                <Route path="/reservation/:id" element={<Reservation/>} />
            </Route>
            <Route element={ <ProtectedRoutes isAllowed={!(isConnected())} to={"/"} /> }>
                <Route path="/authentication" element={<Authentication />} />
            </Route>
            <Route path="*" element={<NotFound/>}/>
        </Routes>
    )
}

/*
                <Route path="/review" element={<CreateReview/>} />
                <Route path="/reservations" element={<AllReservations/>} />


 */