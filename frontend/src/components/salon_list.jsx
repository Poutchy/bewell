import React from "react";

export function SalonList(props) {
    return (
        <div
            className={`salon-list-item ${props.isSelected ? "selected" : ""}`}
            onClick={() => props.onSelect(props.salon)}
        >
            <div>
                <img src={props.salon.imageUrls[0]}/>
            </div>
            <div>
                <h3 className="salon-list-title">{props.salon.name}</h3>
                <p className="salon-list-subtitle">
                    {props.salon.address} - {props.salon.email}
                </p>
            </div>
        </div>
    );
}
