import React from "react";

export function ReservationInformation(props) {

    const id = props.id;

    return (
        <div>
            <p>Information of the reservation: {id}</p>
        </div>
    )
}