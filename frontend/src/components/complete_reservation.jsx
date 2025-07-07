import React from "react";

export function CompleteReservation(props) {
    const salon = props.salon
    const service = props.service;
    const details = props.details

    return (
        <div>
            <div>
                <h1>Reservation Complete!</h1>
            </div>
            <div>
                <p>At {salon.name} from {details.tStart} to {details.tEnd}</p>
            </div>
            <div>
                <p>{service.name} by {details.employeeName}</p>
            </div>
        </div>
    )
}
