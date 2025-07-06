import React, { useEffect, useState } from "react";
import {
    TextField,
    Button,
    MenuItem, Box, Typography, Modal,
} from "@mui/material";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import { AdapterDateFns } from "@mui/x-date-pickers/AdapterDateFns";
import { TimePicker } from "@mui/x-date-pickers/TimePicker";
import { addMinutes, format as formatDate } from "date-fns";
import { TakeAllEmployees } from "../services";
import { Link } from "react-router-dom";

export function BookingForm(props) {
    const salon = props.salon;
    const service = props.service;
    const salonId = salon.id;
    const serviceId = service.id;
    const duration = service.duration;
    const slotList = salon.timeSlots;

    const [tStart, setTStart] = useState(null);
    const [tEnd, setTEnd] = useState(null);
    const [slotId, setSlotId] = useState(0);
    const [employeeId, setEmployeeId] = useState(0);
    const [employeeName, setEmployeeName] = useState("");
    const [employeeList, setEmployeeList] = useState([]);
    const [submitted, setSubmitted] = useState(false);
    const [open, setOpen] = useState(false);
    const handleOpen = () => setOpen(true);
    const handleClose = () => setOpen(false);

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

        if (employeeId === 0 || slotId === 0 || !tStart) {
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
        handleOpen();
    };

    const showEmployeeError = submitted && employeeId === 0;
    const showSlotError = submitted && slotId === 0;
    const showTimeError = submitted && !tStart;

    return (
        <LocalizationProvider dateAdapter={AdapterDateFns}>
            <form
                className="booking-form"
                onSubmit={handleSubmit}
                style={{ display: "grid", gap: "1rem", width: 300 }}
            >
                <TextField
                    label="Salon"
                    value={salon.name}
                    slotProps={{ input: { readOnly: true } }}
                />

                <TextField
                    label="Service"
                    value={service.name}
                    slotProps={{ input: { readOnly: true } }}
                />

                <TimePicker
                    label="Start Time"
                    value={tStart}
                    onChange={setTStart}
                    error={showTimeError}
                    helperText={showTimeError ? "Please select the starting time." : ""}
                />

                <TimePicker label="End Time" value={tEnd} readOnly disabled={!tEnd} />

                <TextField
                    label="Time Slots"
                    select
                    value={slotId}
                    onChange={(e) => setSlotId(Number(e.target.value))}
                    error={showSlotError}
                    helperText={showSlotError ? "Please select a time slot." : ""}
                >
                    <MenuItem value={0}>-------</MenuItem>
                    {slotList.map((time, index) => (
                        <MenuItem key={index} value={time.id}>
                            {time.startTime} - {time.endTime}
                        </MenuItem>
                    ))}
                </TextField>

                <TextField
                    select
                    label="Employee"
                    value={employeeId}
                    onChange={(e) => {
                        const selectedId = Number(e.target.value);
                        setEmployeeId(selectedId);

                        const selectedEmp = employeeList.find(emp => emp.id === selectedId);
                        console.log(selectedEmp);
                        setEmployeeName(selectedEmp.name);
                    }}
                    error={showEmployeeError}
                    helperText={showEmployeeError ? "Please select an employee." : ""}
                >
                    <MenuItem value={0}>-------</MenuItem>
                    {employeeList.map((emp, index) => (
                        <MenuItem key={index} value={emp.id}>
                            {emp.name}
                        </MenuItem>
                    ))}
                </TextField>

                <Button type="submit" variant="contained">
                    Submit
                </Button>
            </form>
            <Modal
                open={open}
                onClose={handleClose}
                aria-labelledby="modal-modal-title"
                aria-describedby="modal-modal-description"
            >
                <Box className="modal-box" sx={
                    {
                        position: 'absolute',
                        top: '50%',
                        left: '50%',
                        transform: 'translate(-50%, -50%)',
                        width: 400,
                        bgcolor: 'background.paper',
                        border: '2px solid #000',
                        boxShadow: 24,
                        p: 4,
                    }
                }>
                    <div>
                        <Typography id="modal-modal-title" variant="h6" component="h2">
                            You choose {service.name} at {salon.name}
                        </Typography>
                    </div>
                    <Typography id="modal-modal-description" sx={{ mt: 2 }}>
                        From {tStart ? formatDate(tStart, "h:mm a") : null} to {tEnd ? formatDate(tEnd, "h:mm a") : null}, made by {employeeName}
                    </Typography>
                    <Link to="/payement">
                        Pay your reservation
                    </Link>
                </Box>
            </Modal>
        </LocalizationProvider>
    );
}
