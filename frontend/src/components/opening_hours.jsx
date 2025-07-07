import React from "react";

export function OpeningHours(props) {
    // Get current day name, e.g. "Monday"
    const daysOfWeek = [
        "Sunday",
        "Monday",
        "Tuesday",
        "Wednesday",
        "Thursday",
        "Friday",
        "Saturday",
    ];
    const todayName = daysOfWeek[new Date().getDay()];

    const todayHours = props.openingHours[todayName];

    return (
        <div>
            {todayHours && todayHours.toLowerCase() !== "Closed" ? (
                <p>Open today from {todayHours}</p>
            ) : (
                <p>Closed today</p>
            )}
        </div>
    );
    // return (
    //     <div>
    //         <h3>Opening Hours</h3>
    //         <ul style={{ listStyle: "none", padding: 0 }}>
    //             {Object.entries(props.openingHours).map(([day, hours]) => (
    //                 <li key={day} style={{ marginBottom: 4 }}>
    //                     <strong>{day}:</strong> {hours}
    //                 </li>
    //             ))}
    //         </ul>
    //     </div>
    // );
}
