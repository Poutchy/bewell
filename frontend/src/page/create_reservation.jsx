import React from "react";
import { useLocation } from "react-router-dom";
import { BookingForm } from "../components";

export function CreateReservation() {
    const location = useLocation();
    const { salon, service } = location.state || {};
    if (salon && service) {
        console.log("salon", salon)
        console.log("service", service)
    }
    return (
        <div>
            <p>Creation of Reservation</p>
            <BookingForm salon={salon} service={service} />
        </div>
    )
}
