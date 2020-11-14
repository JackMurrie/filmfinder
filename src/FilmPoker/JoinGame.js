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
      marginTop: theme.spacing(8),
      display: 'flex',
      flexDirection: 'column',
      alignItems: 'center',
    },
    textField: {
        width: 500,
        margin: 20,
    },
    resize:{
        fontSize:50
      },
    buttonSize: {
        width: 300,
        margin: 50,
    },
}));

export default function JoinGame() {
    const classes = useStyles();
    const history = useHistory();

    const [nickname, setNickname] = React.useState('');
    const [GameID, setGameID] = React.useState(1234);

    const handleSubmit = (event) => {
        event.preventDefault();

        //POST rest/joinGame
        const data = {
            nickname: nickname,
            GameID: GameID,
        };
        //if response okay
        history.push(`/PlayFilmPoker/${data.GameID}`, data);
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
                        placeholder="nickname"
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
                    <TextField
                        variant="outlined"
                        margin="normal"
                        required
                        fullWidth
                        id="GameID"
                        placeholder="Game ID"
                        name="GameID"
                        onChange={event => setGameID(event.target.value)}
                        size='large'
                        InputProps={{
                            classes: {
                              input: classes.resize,
                            },
                          }}
                          className={classes.textField}
                    />
                    </div>
                </Container>
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
                        Join Game
                    </Button>
                    </div>
        </React.Fragment>
    )
}