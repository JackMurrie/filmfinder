export async function getMovies() {
    const response = await fetch('https://asia-east2-csesocworkshop.cloudfunctions.net/api/getMovies');
    return await response.json();
}