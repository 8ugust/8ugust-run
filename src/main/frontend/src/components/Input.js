import { Box, TextField } from "@mui/material";


const Input = ({id, label, value, fnChange, req=false}) => {

    const handleChange = (v) => {
        // Validation Phone
        if (String(label).toLocaleLowerCase() === 'phone' && v) {
            const phone = String(v);
            if (!phone[phone.length-1].match(/\d|\-/)) { fnChange(phone.substring(0, phone.length-1)); return false; }  // eslint-disable-line
            if (phone.length === 4 && phone[3] !== '-') { fnChange(phone.substring(0, 3) + '-' + phone.substring(3)); return false; }
            if (phone.length === 9 && phone[8] !== '-') { fnChange(phone.substring(0, 8) + '-' + phone.substring(8)); return false; }
            if (phone.length > 13) { fnChange(phone.substring(0, 13)); return false; }
        }

        fnChange(v);
    }

    return (<>
        <Box sx={{background:'white', borderRadius:'20px', marginBottom: '20px'}}>
            <TextField 
                id={id} size='small' label={label || 'Input'} required={req}
                value={value} onChange={(e) => handleChange(e.target.value)}
                sx={{'& fieldset':{borderRadius: '20px'}, width:'100%'}} 
            />
        </Box>
    </>)
}

export default Input;