import React from "react";
import {useParams} from "react-router-dom";
import {GoBack, ReservationInformation} from "../components";

export function Reservation(){
    const id = useParams().id;

    return (
        <div>
            <GoBack/>
            <p>Reservation page</p>
            <ReservationInformation id={id} />
        </div>
    )
}
