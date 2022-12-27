import { Box, TextField } from "@mui/material";


const Input = ({id, label, value, fnChange}) => {

    return (<>
        <Box sx={{background:'white', borderRadius:'20px', marginBottom: '20px'}}>
            <TextField 
                id={id} size='small' label={label || 'Input'} 
                value={value} onChange={(v) => fnChange(v.target.value)}
                sx={{'& fieldset':{borderRadius: '20px'}, width:'100%'}} 
            />
        </Box>
    </>)
}

export default Input;