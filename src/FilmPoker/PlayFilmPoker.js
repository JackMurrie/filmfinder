import Header from '../components/Header';
import MovieCard from '../components/MovieCard';
import Footer from '../components/Footer';
import PersonCard from './components/personCard';
import PokerCard from './components/pokerCard';
import VoteCard from './components/voteCard';

import React, { useEffect } from 'react';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import TextField from '@material-ui/core/TextField';
import Grid from '@material-ui/core/Grid';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import { useLocation } from "react-router-dom";
import Async from 'react-async';
import PropTypes from 'prop-types';
import AppBar from '@material-ui/core/AppBar';
import Tabs from '@material-ui/core/Tabs';
import Tab from '@material-ui/core/Tab';
import FavoriteIcon from '@material-ui/icons/Favorite';
import VisibilityIcon from '@material-ui/icons/Visibility';
import ThumbUp from '@material-ui/icons/ThumbUp';
import Box from '@material-ui/core/Box';
import Container from '@material-ui/core/Container';
import SearchIcon from '@material-ui/icons/Search';
import Stepper from "@material-ui/core/Stepper";
import Step from "@material-ui/core/Step";
import StepLabel from "@material-ui/core/StepLabel";
import { useFetch, IfFulfilled, IfPending, IfRejected } from 'react-async';
import Backdrop from '@material-ui/core/Backdrop';
import CircularProgress from '@material-ui/core/CircularProgress';


const useStyles = makeStyles((theme) => ({
    paper: {
      margin: theme.spacing(1),
      display: 'flex',
      flexDirection: 'column',
      alignItems: 'center',
      flexWrap: "wrap",
      height: 165,
      width: 165,
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
    headText: {
        textAlign: "center",
        margin: 50,
        fontFamily: ['Montserrat', "sans-serif"],
    },
    largeIcon: {
        width: 125,
        height: 125,
    },
    buttonStyle: {
        marginTop: theme.spacing(8),
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
      },
    resize:{
        fontSize:20,
    },
    textField: {
        width: 500,
        margin: 20,
    },
    center: {
        alignItems: 'center',
    },
    root: {
        width: "100%"
    },
    button: {
        marginRight: theme.spacing(1),
        marginTop: theme.spacing(5),
        marginBottom: theme.spacing(5)
    },
    instructions: {
        marginTop: theme.spacing(1),
        marginBottom: theme.spacing(1)
    },
    container: {
      display: "flex",
      flexWrap: "wrap",
    },
    right: {
      textAlign: "right",
    },
    center: {
        textAlign: "center",
        alignItems: 'center',
      },
}));

export default function PlayFilmPoker() {
    const classes = useStyles();
    const location = useLocation();
    const GameID = parseInt(location.pathname.split('/').pop(), 10);
    const nickname = location.state.nickname;

    return (
        <React.Fragment>
        <CssBaseline />
            <Header />
            <Container component="main" maxWidth="lg" >
                    <Typography component="h1" variant="h4" className={classes.headText}>
                        Hello {nickname}! Invite others to join with your code: {GameID}
                    </Typography>
                            
                    <HorizontalLinearStepper />
            </Container>
            <Footer />
        </React.Fragment>
    )
}

function TabPanel(props) {
    const { children, value, index, ...other } = props;

    return (
        <div
        role="tabpanel"
        hidden={value !== index}
        id={`scrollable-force-tabpanel-${index}`}
        aria-labelledby={`scrollable-force-tab-${index}`}
        {...other}
        >
        {value === index && (
            <Box p={3}>
            <Typography>{children}</Typography>
            </Box>
        )}
        </div>
    );
}
  
TabPanel.propTypes = {
    children: PropTypes.node,
    index: PropTypes.any.isRequired,
    value: PropTypes.any.isRequired,
};
  
function a11yProps(index) {
    return {
        id: `full-width-tab-${index}`,
        'aria-controls': `full-width-tabpanel-${index}`,
    };
}

function TabButtons() {
    const classes = useStyles();
    const [value, setValue] = React.useState(0);
    const [title, setSearch] = React.useState('');

    const handleChange = (event, newValue) => {
    setValue(newValue);
    };

    const requestOptions = {
        method: 'POST',
        headers: { 
            'Accept': 'application/json',
            'Content-Type': 'application/json' 
        },
        body: JSON.stringify({searchString: title})
    };
    const state = useFetch('/rest/search', requestOptions);

    useEffect(state.run, [title]);

    const handleResults = ({ movies }) => {
        const searchResults = movies.map(({ movieId, name, year, imageUrl }) => {
            return <PokerCard key={movieId} movieId={movieId} title={name} yearReleased={year} imageUrl={imageUrl}/>
        });
        return searchResults;
    };

    return (
    <div className={classes.root}>
        <AppBar position="static" color="default">
        <Tabs
            value={value}
            onChange={handleChange}
            variant="fullWidth"
            indicatorColor="primary"
            textColor="primary"
            aria-label="Buton Tabs"
        >
            <Tab label="From Wishlist" icon={<FavoriteIcon />} {...a11yProps(1)} />
            <Tab label="From Recommended" icon={<ThumbUp />} {...a11yProps(2)} />
            <Tab label="Search" icon={<SearchIcon />} {...a11yProps(3)} />
            
        </Tabs>
        </AppBar>
        <TabPanel value={value} index={0}>
          <PokerCard title={"A Movie"}/>
        </TabPanel>
        <TabPanel value={value} index={1}>
          <PokerCard title={"A Movie"}/>
        </TabPanel>
        <TabPanel value={value} index={2}>
            <div className={classes.center}>
            <TextField
                variant="outlined"
                margin="normal"
                fullWidth
                id="search"
                placeholder="Search Movies"
                name="search"
                size='large'
                onChange={(event) => setSearch(event.target.value)}
                InputProps={{
                    classes: {
                        input: classes.resize,
                    },
                    }}
                    className={classes.textField}
            />
            </div>
              <div className={classes.container}>
                <IfRejected state={state}>Error...</IfRejected>
                <IfPending state={state}>
                </IfPending>
                <IfFulfilled state={state}>{handleResults}</IfFulfilled>
              </div>

        </TabPanel>
    </div>
    );
}


function getSteps() {
  return ["Choose Movies", "Confirm Movies", "Wait for other Players", "Vote", "View Results"];
}

function GetStepContent(step) {
    const classes = useStyles();

    const [vote, setVote] = React.useState(1);

    function updateVoteNumber(props)  {
      console.log(props);
      console.log(vote);
      if (props) {
        setVote(vote - 1);
      }
      else {
        setVote(vote + 1);
      }
    };

    //GET nicknames of other players
    const names = [
        "Jack",
        "Quoc-An",
        "Kristian",
        "Hikari",
        "Hai",
    ];

  switch (step) {
    case 0:
      return (
        <React.Fragment>
            <TabButtons />
        </React.Fragment>
      );
    case 1:
      return (
        <React.Fragment>
            <div>movie card</div>
        </React.Fragment>
      );
    case 2:
      return (
        <React.Fragment>
        <Grid container spacing={3} >
            {names.map((name) => (
                <PersonCard name={name}/>
            ))}
        </Grid>
    </React.Fragment>
      );
    case 3:
      return (
        <React.Fragment>
          <Typography component="h1" variant="h4" className={classes.headText}>
              Vote number {vote}
          </Typography>
          <div className={classes.container}>
            <VoteCard currentVote={vote} updateVoteNumber={updateVoteNumber}/>
            <VoteCard currentVote={vote} updateVoteNumber={updateVoteNumber}/>
          </div>
        </React.Fragment>
      );
    case 4:
        return "Chosen movie card";
  }
}

function HorizontalLinearStepper() {
  const classes = useStyles();
  const [activeStep, setActiveStep] = React.useState(0);
  const steps = getSteps();

  const handleNext = () => {
    setActiveStep((prevActiveStep) => prevActiveStep + 1);
  };

  const handleBack = () => {
    setActiveStep((prevActiveStep) => prevActiveStep - 1);
  };

  return (
    <div className={classes.root}>
        <div>
          <Button
            disabled={activeStep === 0}
            onClick={handleBack}
            className={classes.button}
          >
            Back
          </Button>
          {activeStep === steps.length - 1 ? 
          (<Button
            variant="contained"
            color="primary"
            className={classes.button}
            href="/"
          >
            Finish
          </Button>
          ) : (<Button
            variant="contained"
            color="primary"
            onClick={handleNext}
            className={classes.button}
          >
            Next
          </Button>)}
      </div>
      <Stepper activeStep={activeStep}>
        {steps.map((label, index) => {
          const stepProps = {};
          const labelProps = {};
          return (
            <Step key={label} {...stepProps}>
              <StepLabel {...labelProps}>{label}</StepLabel>
            </Step>
          );
        })}
      </Stepper>
          <div>
            <Typography component={'span'} className={classes.instructions}>
              {GetStepContent(activeStep)}
            </Typography>
            <div>
              <Button
                disabled={activeStep === 0}
                onClick={handleBack}
                className={classes.button}
              >
                Back
              </Button>
              {activeStep === steps.length - 1 ? 
              (<Button
                variant="contained"
                color="primary"
                className={classes.button}
                href="/"
              >
                Finish
              </Button>
              ) : (<Button
                variant="contained"
                color="primary"
                onClick={handleNext}
                className={classes.button}
              >
                Next
              </Button>)}
            </div>
          </div>
    </div>
  );
}
