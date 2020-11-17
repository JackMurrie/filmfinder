import React from 'react';
import Button from '@material-ui/core/Button';
import Grid from '@material-ui/core/Grid';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import CardHeader from '@material-ui/core/CardHeader';
import Link from '@material-ui/core/Link';
import IconButton from '@material-ui/core/IconButton';
import DeleteIcon from '@material-ui/icons/Delete';
import EditIcon from '@material-ui/icons/Edit';
import CardContent from '@material-ui/core/CardContent';
import Rating from '@material-ui/lab/Rating';
import Box from '@material-ui/core/Box';
import TextField from '@material-ui/core/TextField';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';

import { useAsync, useFetch } from 'react-async';
import { CardActions } from '@material-ui/core';

const useStyles = makeStyles((theme) => ({
    '@global': {
      ul: {
        margin: 0,
        padding: 0,
        listStyle: 'none',
      },
    },
    paper: {
      padding: theme.spacing(2),
      display: 'flex',
      overflow: 'auto',
      flexDirection: 'column',
    },
    right: {
      marginLeft: 900,
    },
    title: {
      color: theme.palette.text.primary
    },
    control: {
      marginLeft: 'auto'
    }
  })
);

const updateHeaders = {
  method: 'PUT',
  headers: {
    'Accept': 'application/json',
    'Content-Type': 'application/json'
  }
};

const deleteHeaders = {
  method: 'DELETE',
  headers: {
    'Accept': 'application/json',
  }
};


export default function PrivateReview(props) {
    const classes = useStyles();
    
    const removeReviewAndReload = async () => {
      await fetch(`/rest/review/${props.movieId}`, deleteHeaders);
      props.onChange();
    };

    const updateRating = async (event, newRating) => {
      event.preventDefault();
      
      const data = {
        rating: newRating,
      };
      await fetch(`/rest/rating/${props.movieId}`, {...updateHeaders, body: JSON.stringify(data)});
      props.onChange();
    };

    const updateReview = async (event, newComment) => {
      event.preventDefault();

      const data = {
        comment: newComment,
      };

      await fetch(`/rest/review/${props.movieId}`, {...updateHeaders, body: JSON.stringify(data)});
      props.onChange();
    };

    return (
        <Grid item xs={12}>
            <Card style={{width: 1150, margin: 10}}>
              <CardHeader
                title={<Link href={`/Movie/${props.movieId}`} className={classes.title} style={{ fontSize: '30px' }}>{props.title}</Link>}
                action={
                  <Box component="fieldset" mb={-1} borderColor="transparent" marginTop={5}>
                    <Rating name="read-only" readOnly precision={0.5} value={props.rating} />
                  </Box>
                }
              />
            <CardContent>
              {props.text}
            </CardContent>
            <CardActions className={classes.right}>
              <EditReviewButton movieId={props.movieId} oldReview={props.text} onSubmit={updateReview}/>
              <IconButton color="primary" component="span" className={classes.control} onClick={removeReviewAndReload}>
                <DeleteIcon />
              </IconButton>
              <Button disabled color="secondary">
                {props.postDate}
            </Button>
            </CardActions>
          </Card>
        </Grid>
    );
  }

  function EditReviewButton(props) {
    const classes = useStyles();
    const [open, setOpen] = React.useState(false);
    const [newComment, setNewComment] = React.useState('');
  
    const openReviewDialogBox = () => {
      setOpen(true);
    };
  
    const closeReviewDialogBox = () => {
      setOpen(false);
    };
  
    return (
      <div>
        <IconButton color="primary" component="span" className={classes.control} onClick={openReviewDialogBox}>
              <EditIcon />
        </IconButton>
        <Dialog fullScreen={true} open={open} onClose={closeReviewDialogBox} aria-labelledby="form-dialog-title">
          <form onSubmit={(event) => { props.onSubmit(event, newComment); }}>
            <DialogTitle id="form-dialog-title">Review</DialogTitle>
            <DialogContent>
              <DialogContentText>
                Edit your review here...
              </DialogContentText>
              <TextField
                autoFocus
                multiline
                rowsMax={10}
                margin="dense"
                id="review"
                label=""
                fullWidth={true}
                defaultValue={props.oldReview}
                onChange={(event) => setNewComment(event.target.value)}
              />
            </DialogContent>
            <DialogActions>
              <Button onClick={closeReviewDialogBox} color="primary">
                Cancel
              </Button>
              <Button onClick={closeReviewDialogBox} color="primary" type="submit">
                Edit Review
              </Button>
            </DialogActions>
          </form>
        </Dialog>
      </div>
    );
  }