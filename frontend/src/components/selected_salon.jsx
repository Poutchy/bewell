import React from "react";
import {OpeningHours} from "./opening_hours";

export function SelectedSalon(props) {
    return (
        <div>
            <div>

                <div>
                    <img src={props.salon.imageUrls[0]}/>
                </div>
                <div>
                    <h1>{props.salon.name}</h1>
                    <p>{props.salon.rating}</p>
                </div>
            </div>
            <p>{props.salon.description}</p>
            <p>{props.salon.email} - {props.salon.phone}</p>
            <p>{props.salon.latitude} - {props.salon.longitude}</p>
            <p>{props.salon.pricingRange}</p>
            <OpeningHours openingHours={props.salon.openingHours}/>
        </div>
    )
}

