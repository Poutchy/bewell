import React from "react";
import {useParams} from "react-router-dom";

export function Review(){
    const id = useParams().id;

    return (
        <div>
            <p>Review {id} Visualisation</p>
        </div>
    )
}
