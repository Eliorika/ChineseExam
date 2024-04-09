var isSubmittable = false;

function skipQuestion(isSkip) {
    console.log(isSkip)
    var form = document.getElementById('question_form');
    // Добавляем параметр запроса skip=true к URL формы
    form.action = form.action + "?skip=" + isSkip;
    // Отправляем форму
    //form.submit();
}

window.addEventListener("beforeunload", function(event) {
    // Если форма была отправлена, не показывать предупреждение
    if (!isSubmittable) {
        // Устанавливаем текст предупреждения
        var confirmed = confirm("Ваш прогресс будет потерян. Вы уверены?");
        if (confirmed) {
            // Пользователь отказался сохранить прогресс, поэтому просто возвращаем стандартное сообщение
            // Пользователь согласился сохранить прогресс, поэтому выполняем запрос на бэкэнд
            var xhr = new XMLHttpRequest();
            xhr.open("POST", "/test/clear", true);
            xhr.setRequestHeader("Content-Type", "application/json");
            xhr.onreadystatechange = function () {
                if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
                    // Действия после успешного выполнения запроса
                }
            };
            xhr.send();
        } else {
            event.preventDefault();
            event.returnValue = '';
        }
    }
});

function selectVariant(element) {
    var variant = element.getAttribute('data-variant');
    document.querySelector("input[name='answer']").value = variant;
}

function markFormSubmitted() {
    isSubmittable = true;
}

function addListeners() {
    const answers = document.getElementById('answers');
    // const answers = document.querySelector('.question_answers');

    answers.addEventListener('click', event => {
        let target = event.target;
        let isChosen = target.classList.contains('card') || target.tagName == 'LABEL' || target.tagName == 'INPUT';
        if(isChosen) {
            deleteClassChosen();
            chooseCard(target);
        }
    })
}

function chooseCard(target) {
    let parent = target.closest('.card');

    parent.classList.add('chosenCard');
}

function deleteClassChosen() {
    const cards = document.querySelectorAll('.card');

    cards.forEach(elem => {
        elem.classList.remove('chosenCard');
    })
}