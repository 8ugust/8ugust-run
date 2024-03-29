import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider/LocalizationProvider';
import { MobileDatePicker } from '@mui/x-date-pickers/MobileDatePicker';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs'
import { TextField } from '@mui/material';

const Calendar = ({label, date, fnChange, req=false}) => {

    return(<>
        <LocalizationProvider dateAdapter={AdapterDayjs}>
            <MobileDatePicker 
                mask='____-__-__'
                inputFormat='YYYY-MM-DD'
                label={label || 'Birth'} 
                value={date} onChange={(v) => fnChange(v)} 
                renderInput={p => <TextField 
                    {...p}
                    size='small' required={req}
                    sx={{'& fieldset':{borderRadius: '20px'}, width:'45%'}}
                />} 
            />
        </LocalizationProvider>
    </>)
}

export default Calendar;