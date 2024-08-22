async function getProfileData() {
    const url = 'https://raw.githubusercontent.com/Matheus-D-R-Costa/Webpage-Portfolio/main/data/profile.json?token=GHSAT0AAAAAACOVD2BKRJSASCI4WDHSIVHQZP7JBXQ'
    const getting = await fetch(url)
    return await getting.json()
}