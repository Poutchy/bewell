import React from "react";

export function OpeningHours(props) {
    return (
        <div>
            <h3>Opening Hours</h3>
            <ul style={{ listStyle: "none", padding: 0 }}>
                {Object.entries(props.openingHours).map(([day, hours]) => (
                    <li key={day} style={{ marginBottom: 4 }}>
                        <strong>{day}:</strong> {hours}
                    </li>
                ))}
            </ul>
        </div>
    );
}
