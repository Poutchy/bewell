import React from "react";
import {OpeningHours} from "./";
import {ServiceList} from "./";

export function SelectedSalon(props) {
    const salon = props.salon
    const services = salon.services;

    return (
        <div>
            <div>

                <div>
                    <img src={salon.imageUrls[0]}/>
                </div>
                <div>
                    <h1>{salon.name}</h1>
                    <p>{salon.rating}</p>
                </div>
            </div>
            <p>{salon.description}</p>
            <p>{salon.email} - {salon.phone}</p>
            <p>{salon.latitude} - {salon.longitude}</p>
            <p>{salon.pricingRange}</p>
            <OpeningHours openingHours={salon.openingHours}/>
            {
                services.map((service, index) => (
                    <ServiceList
                        key={index}
                        salon={salon}
                        service={service}
                    />
                ))
            }
        </div>
    )
}

