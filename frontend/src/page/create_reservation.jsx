import React from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { BookingForm } from "../components";

export function CreateReservation() {
    const navigate = useNavigate();

    const location = useLocation();
    const { salon, service } = location.state || {};
    if (salon && service) {
        console.log("salon", salon)
        console.log("service", service)

        return (
            <div
                className="booking-form-container"
            >
                <BookingForm salon={salon} service={service} />
            </div>
        )
    } else {
        navigate("/salons");
    }
}
