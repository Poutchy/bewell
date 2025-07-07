import {Route, Routes} from "react-router-dom";
import {HomePage, CreateReservation, PaymentSuccessPage, PaymentCancelPage, NotFound, SalonsVisualisation, Reviews, Review, Authentication} from "./page";
import {ProtectedRoutes} from "./components";
import {useIsConnected} from "./interface";

export function AppRoutes() {
    const isConnected = useIsConnected();

    return (
        <Routes>
            <Route path="/" element={<HomePage/>}/>
            <Route path="/salons" element={<SalonsVisualisation/>}/>
            <Route path="/reviews" element={<Reviews/>} />
            <Route path="/review/:id" element={<Review/>} />

            <Route element={ <ProtectedRoutes isAllowed={isConnected()} to={"/authentication"} /> }>
                <Route path="/reservation" element={<CreateReservation />} />
                <Route path="/payment/success" element={<PaymentSuccessPage />} />
                <Route path="/payment/cancel" element={<PaymentCancelPage />} />
            </Route>

            <Route element={ <ProtectedRoutes isAllowed={!(isConnected())} to={"/reservation"} /> }>
                <Route path="/authentication" element={<Authentication />} />
            </Route>
            <Route path="*" element={<NotFound/>}/>
        </Routes>
    )
}

/*
                <Route path="/review" element={<CreateReview/>} />
                <Route path="/reservations" element={<AllReservations/>} />
                <Route path="/payement" element={<Payement/>} />




 */