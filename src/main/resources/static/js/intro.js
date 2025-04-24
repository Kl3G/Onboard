

document.addEventListener('DOMContentLoaded', function () {

    const slogan = document.querySelector('.slogan');
    slogan.classList.add('show');
});

window.addEventListener('scroll', () => {

    const slogan = document.querySelector('.slogan');

    const intro1 = document.querySelector('.intro1');

    const intro2 = document.querySelector('.intro2');

    const sloganRect = slogan.getBoundingClientRect();

    const rect1 = intro1.getBoundingClientRect();

    const rect2 = intro2.getBoundingClientRect();

    if (sloganRect.bottom > window.innerHeight * 0.35) {

        slogan.classList.remove('hide');
        slogan.classList.add('show');

        intro1.classList.remove('show');
        intro1.classList.add('hide');
    }

    if (rect1.top < window.innerHeight * 0.35) {

        slogan.classList.add('hide');
        intro2.classList.add('hide');

        slogan.classList.remove('show');
        intro1.classList.remove('hide');
        intro1.classList.add('show');
    }

    if (rect2.top < window.innerHeight * 0.35) {

        slogan.classList.add('hide');
        intro2.classList.remove('hide');

        intro1.classList.remove('show');
        intro2.classList.add('show');
        /*document.body.classList.add('after-colored');*/
    }
});