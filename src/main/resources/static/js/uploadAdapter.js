

    /*function create(element, config) {
        return new Promise((resolve, reject) => {
            // 1. HTML 요소(element)를 기반으로 에디터 인스턴스를 생성합니다.
            let editorInstance = new EditorInstance(element);

            // 2. (비동기) 초기화 작업을 수행합니다.
            editorInstance.initialize(config)
                .then(() => {
                    // 3. 초기화가 완료되면 extraPlugins 같은 설정들을 적용합니다.
                    if (config.extraPlugins) {
                        config.extraPlugins.forEach(plugin => { // plugin 은 MyCustomUploadAdapterPlugin 함수다.
                            plugin(editorInstance); // editorInstance 는 document.querySelector('#editor') 이다.
                        });
                    }
                    // 4. 에디터 인스턴스가 준비되면 Promise를 성공적으로 완료합니다.
                    resolve(editorInstance);
                })
                .catch(error => {
                    // 초기화 과정에서 문제가 발생하면 Promise를 실패 상태로 만듭니다.
                    reject(error);
                });
        });
    }*/


    // CKEditor 초기화 및 custom upload adapter 플러그인 적용
    ClassicEditor.create(document.querySelector('#editor'), {
    // ClassicEditor.create() 함수는 Promise 객체를 반환하고, 보통 두 가지 인자를 받는다.
    // 1. HTML 요소(또는 해당 요소를 찾는 CSS 선택자) 2. 설정 옵션들을 담은 객체

        extraPlugins: [ MyCustomUploadAdapterPlugin ],
        language: 'ko', // 기본 빌드는 영어 UI만 포함되어 있을 가능성이 높기 때문에 적용 안 된다.
        // 필요한 다른 설정들을 추가할 수 있습니다.
    }).then(editor => { console.log('에디터 준비완료 : ', editor); })
    .catch(error => {

        console.error(error);
    });


    // Custom Upload Adapter Plugin 함수 정의
    function MyCustomUploadAdapterPlugin(editor) {

        editor.plugins.get('FileRepository').createUploadAdapter = (loader) => {
        // 에디터 인스턴스를 생성하면, 그 안에는 이미 FileRepository와 같은
        // 기본 플러그인들이 내장되어 있기 때문에 'FileRepository'를 불러올 수 있다.

            const adapter = new UploadAdapter(loader);
            adapter.upload().then(result => {
                console.log(result); // { default: '업로드된 이미지의 URL' }가 출력됩니다.
            });

            return new adapter(loader);
        };
    }


    // Custom Upload Adapter 클래스 정의
    class UploadAdapter {

        constructor(loader) {

            this.loader = loader;
        }

        upload() {

            return this.loader.file.then(file => new Promise((resolve, reject) => {

                    this.initRequest(); // 서버 요청을 보낼 준비
                    this.initListeners(resolve, reject, file); // 요청의 성공, 실패를 감시
                    this.sendRequest(file); // 실제로 서버에 파일 전송
                })
            );
        }

        initRequest() { // 서버 요청을 보낼 준비

            const xhr = this.xhr = new XMLHttpRequest();
            // 업로드를 처리할 서버 엔드포인트 (백엔드에서 해당 URL을 처리해야 합니다)
            xhr.open('POST', 'https://port-0-onboard-m7n11lrga828c347.sel4.cloudtype.app/upload-image', true);
            xhr.responseType = 'json';
        }

        sendRequest(file) { // 실제로 서버에 파일 전송

            const data = new FormData(); // FormData 는 HTML form 의 데이터를 key-value 쌍으로 구성하여 전송할 수 있게 해주는 객체.
            data.append('upload', file); //  생성한 FormData 객체에 key 'upload' 와 값으로 file 객체를 추가.
            this.xhr.send(data); // this.xhr 를 통해 서버로 데이터를 전송.
        }

        initListeners(resolve, reject, file) { // 요청의 성공, 실패를 감시

            const xhr = this.xhr;
            const genericErrorText = '파일을 업로드 할 수 없습니다.';

            xhr.addEventListener('error', () => reject(genericErrorText));
            xhr.addEventListener('abort', () => reject());
            xhr.addEventListener('load', () => {
                const response = xhr.response;

                if (!response || response.error) {

                    return reject(response && response.error ? response.error.message : genericErrorText);
                }

                resolve({

                    default: response.url // 업로드 후 반환된 이미지 URL
                });
            });
        }
    }