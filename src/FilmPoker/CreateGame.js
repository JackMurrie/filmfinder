import Header from '../components/Header';

import React from 'react';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import TextField from '@material-ui/core/TextField';
import { makeStyles } from '@material-ui/core/styles';
import Container from '@material-ui/core/Container';
import { useHistory } from 'react-router-dom';

const useStyles = makeStyles((theme) => ({
    paper: {
      marginTop: theme.spacing(15),
      display: 'flex',
      flexDirection: 'column',
      alignItems: 'center',
    },
    textField: {
        width: 500,
        margin: 50,
    },
    resize:{
        fontSize:50
      },
    buttonSize: {
        width: 300,
        margin: 50,
    },
}));

export default function CreateGame() {
    const classes = useStyles();
    const history = useHistory();

    const [nickname, setNickname] = React.useState('');

    const handleSubmit = (event) => {
        event.preventDefault();
        //GET rest/CreateGame -> returns GameID

        //POST rest/joinGame
        const data = {
            nickname: nickname,
            GameID: 1234,
        };
        
        history.push(`/PokerGame/Play/${data.GameID}`, data);
      }

    return (
        <React.Fragment>
        <CssBaseline />
            <Header />
            <Container component="main" maxWidth="lg">
                <div className={classes.paper}>
                    <TextField
                        variant="outlined"
                        margin="normal"
                        required
                        fullWidth
                        id="nickname"
                        placeholder="Your Nickname"
                        name="nickname"
                        onChange={event => setNickname(event.target.value)}
                        size='large'
                        InputProps={{
                            classes: {
                              input: classes.resize,
                            },
                          }}
                          className={classes.textField}
                    />
                    <div className={classes.paper}>
                    <Button
                        type="submit"
                        fullWidth
                        variant="contained"
                        color="primary"
                        className={classes.buttonSize}
                        size='large'
                        onClick={handleSubmit}
                    >
                        Create Game
                    </Button>
                    </div>
                </div>
                </Container>
        </React.Fragment>
    )
}