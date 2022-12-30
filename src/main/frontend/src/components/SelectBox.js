import { Autocomplete } from "@mui/material";
import { TextField } from "@mui/material";
import { Box } from "@mui/material";

const SelectBox = ({label, options, value, fnChange, req=false}) => {

    const default_options = [
        {id: 'M', name: 'Male'},
        {id: 'F', name: 'Female'}
    ];

    return (<>
        <Box style={{width:'50%'}}>
            <Autocomplete 
                options={options || default_options} 
                getOptionLabel={option => option.name}
                isOptionEqualToValue={(option, value) => option.id === value.id}
                renderInput={p => <TextField 
                    {...p} size='small'
                    value={value} onChange={e => fnChange(e.target.value)}
                    label={label || 'Combo'} required={req}
                    sx={{'& fieldset':{borderRadius: '20px'}}}
                />}
            />
        </Box>
    </>)
}

export default SelectBox;