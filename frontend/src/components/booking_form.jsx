import React, { useEffect, useState } from "react";
import {
    TextField,
    Button,
    MenuItem,
} from "@mui/material";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import { AdapterDateFns } from "@mui/x-date-pickers/AdapterDateFns";
import { TimePicker } from "@mui/x-date-pickers/TimePicker";
import { addMinutes, format as formatDate } from "date-fns";
import { TakeAllEmployees } from "../services";

export function BookingForm(props) {
    const salonId = props.salon.id;
    const serviceId = props.service.id;
    const duration = props.service.duration;

    const [tStart, setTStart] = useState(null);
    const [tEnd, setTEnd] = useState(null);
    const [slotId, setSlotId] = useState(1);
    const [employeeId, setEmployeeId] = useState(0);
    const [employeeList, setEmployeeList] = useState([]);
    const [submitted, setSubmitted] = useState(false);

    const getDurationInMinutes = (iso = "PT0M") => {
        const match = iso.match(/PT(?:(\d+)H)?(?:(\d+)M)?/);
        if (!match) return 0;
        const hours = parseInt(match[1] || "0", 10);
        const minutes = parseInt(match[2] || "0", 10);
        return hours * 60 + minutes;
    };

    useEffect(() => {
        async function fetchEmployees() {
            try {
                const res = await TakeAllEmployees();
                setEmployeeList(res);
            } catch (err) {
                console.error("TakeAllEmployees failed:", err);
            }
        }
        fetchEmployees();
    }, []);

    useEffect(() => {
        if (!tStart) return;
        const minutesToAdd = getDurationInMinutes(duration);
        setTEnd(addMinutes(tStart, minutesToAdd));
    }, [tStart, duration]);

    const handleSubmit = (e) => {
        e.preventDefault();
        setSubmitted(true);

        if (employeeId === 0) {
            // just stop submit, no error popup needed here
            return;
        }

        // Clear errors if any and proceed
        const formData = {
            salonId,
            serviceId,
            slotId,
            employeeId,
            tStart: tStart ? formatDate(tStart, "h:mm a") : null,
            tEnd: tEnd ? formatDate(tEnd, "h:mm a") : null,
        };
        console.log("Submitted:", formData);
    };

    const showEmployeeError = submitted && employeeId === 0;

    return (
        <LocalizationProvider dateAdapter={AdapterDateFns}>
            <form
                onSubmit={handleSubmit}
                style={{ display: "grid", gap: "1rem", width: 300 }}
            >
                <TextField
                    label="Salon"
                    value={props.salon?.name ?? ""}
                    slotProps={{ input: { readOnly: true } }}
                />

                <TextField
                    label="Service"
                    value={props.service?.name ?? ""}
                    slotProps={{ input: { readOnly: true } }}
                />

                <TimePicker label="Start Time" value={tStart} onChange={setTStart} />

                <TimePicker label="End Time" value={tEnd} readOnly disabled={!tEnd} />

                <TextField
                    label="Slot ID"
                    type="number"
                    value={slotId}
                    onChange={(e) => setSlotId(Number(e.target.value))}
                />

                <TextField
                    select
                    label="Employee"
                    value={employeeId}
                    onChange={(e) => setEmployeeId(Number(e.target.value))}
                    error={showEmployeeError}
                    helperText={showEmployeeError ? "Please select an employee." : ""}
                >
                    <MenuItem value={0}>-------</MenuItem>
                    {employeeList.map((emp) => (
                        <MenuItem key={emp.id} value={emp.id}>
                            {emp.name}
                        </MenuItem>
                    ))}
                </TextField>

                <Button type="submit" variant="contained">
                    Submit
                </Button>
            </form>
        </LocalizationProvider>
    );
}
