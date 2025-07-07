import React, { useEffect } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { BookingForm } from "../components";
import "../css/booking.css";

export function PaymentCancelPage() {
    return <h2>❌ Payment was cancelled. No reservation was made.</h2>;
}