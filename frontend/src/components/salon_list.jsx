import React from "react"

export function SalonList(props) {
    return (
        <div
            className={`p-4 border-b cursor-pointer hover:bg-gray-50 ${
                props.isSelected ? "bg-blue-50" : ""
            }`}
            onClick={() => props.onSelect(props.job)}
        >
            <h3 className="text-lg font-semibold">{props.salon.name}</h3>
            <p className="text-sm text-gray-600">{props.salon.address} - {props.salon.email}</p>
        </div>
    );
}