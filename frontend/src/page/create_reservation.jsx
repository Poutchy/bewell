import React, { useEffect } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { BookingForm } from "../components";
import "../css/booking.css";

export function CreateReservation() {
    const navigate = useNavigate();
    const location = useLocation();
    const { salon, service } = location.state || {};

    useEffect(() => {
        if (!(salon && service)) {
            navigate("/salons");
        }
    }, [salon, service, navigate]);

    if (!(salon && service)) {
        return null; // ⏳ Or render a loading spinner or redirect message
    }

    return (
        <div className="booking-form-container">
            <BookingForm salon={salon} service={service} />
        </div>
    );
}
