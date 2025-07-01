import { SalonList, SelectedSalon } from "./";
import React, { useEffect, useState } from "react";
import { TakeAllSalons } from "../services";

export function Salon() {
    const [salonsList, setSalonsList] = useState([]);
    const [selectedSalon, setSelectedSalon] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        async function fetchSalon() {
            try {
                const res = await TakeAllSalons();
                setSalonsList(res);
                setLoading(false);
            } catch (e) {
                console.error("poc", e);
            }
        }

        fetchSalon();
    }, []);


    if (loading) {
        return <div style={{  }}>Loading...</div>;
    }

    return (
        <div class="flex-container">
            <div class="flex-child">
                {salonsList.map((salon) => (
                    <SalonList
                        key={salon.id}
                        salon={salon}
                        isSelected={selectedSalon?.id === salon.id}
                        onSelect={() => setSelectedSalon(salon)}
                    />
                ))}
            </div>
            <div class="flex-child">
                {
                    selectedSalon == null &&
                    <p>You don't have selected an item</p>
                }
                {
                    selectedSalon != null &&
                    <SelectedSalon salon={selectedSalon}/>
                }
            </div>
        </div>
    );
}
