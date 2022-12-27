import { Autocomplete } from "@mui/material";
import { TextField } from "@mui/material";
import { Box } from "@mui/material";

const SelectBox = ({label, options, value, fnChange}) => {

    const default_options = [
        {label: 'Male', data: 'M'},
        {label: 'Female', data: 'F'}
    ];

    return (<>
        <Box style={{width:'50%'}}>
            <Autocomplete 
                options={options || default_options} 
                renderInput={p => <TextField 
                    {...p} size='small'
                    label={label || 'Combo'}
                    sx={{'& fieldset':{borderRadius: '20px'}}}
                />}
            />
        </Box>
    </>)
}

export default SelectBox;