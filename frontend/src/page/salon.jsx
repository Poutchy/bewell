import React from "react";
import {useParams} from "react-router-dom";

export function Salon(){
    const id = useParams().id;

    return (
        <div>
            <p>Salon {id} Visualisation</p>
        </div>
    )
}
