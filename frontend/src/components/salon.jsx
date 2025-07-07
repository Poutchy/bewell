import { SalonList, SelectedSalon } from "./";
import React, { useEffect, useState } from "react";
import { TakeAllSalons } from "../services";

export function Salons() {
    const [salonsList, setSalonsList] = useState([]);
    const [selectedIndex, setSelectedIndex] = useState(null);

    useEffect(() => {
        async function fetchSalon() {
            try {
                const res = await TakeAllSalons();
                console.log(res);
                setSalonsList(res);
            } catch (e) {
                console.error("poc", e);
            }
        }

        fetchSalon();
    }, []);

    const selectedSalon = selectedIndex !== null ? salonsList[selectedIndex] : null;

    return (
        <div className="flex-container">
            <div className="flex-child">
                {salonsList.map((salon, index) => (
                    <SalonList
                        key={index}
                        salon={salon}
                        isSelected={selectedIndex === index}
                        onSelect={() => setSelectedIndex(index)}
                    />
                ))}
            </div>
            <div className="flex-child">
                {
                    selectedIndex == null &&
                    <p>You don't have selected an item</p>
                }
                {
                    selectedIndex != null &&
                    <SelectedSalon salon={selectedSalon}/>
                }
            </div>
        </div>
    );
}
