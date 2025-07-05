import "../css/salons.css"
import React, {useEffect} from "react";
import {Salons} from "../components";
import {useLocation, useNavigate} from "react-router-dom";

export function SalonsVisualisation(){
    const navigate = useNavigate();
    const location = useLocation();
    const { salon, service } = location.state || {};

    useEffect(() => {
        if (salon && service) {
            navigate("/reservation");
        }
    }, [salon, service, navigate]);

    if (salon && service) {
        return null; // ⏳ Or render a loading spinner or redirect message
    }
    return (
        <div>
            <Salons/>
        </div>
    )
}
