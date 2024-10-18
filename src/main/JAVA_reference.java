package com.example.Sep20Join;/*
  1. Entity와 Repository의 역할분담

    Entity는 데이터 "구조"를 정의하는 책임을, Repository는 데이터 "접근"과 관련된 책임을 가진다.
    Entity와 Repository를 하나로 합치는 것은 가능하지만, 아래와 같은 이유로 권장되지 않는다.
    단일 책임 원칙 위배: 클래스는 하나의 책임만 가져야 한다는 원칙에 어긋난다, 두 가지 책임이 한 클래스에 혼합될 수 없다.
    유지보수 어려움: 데이터 구조와 데이터 접근 로직이 섞이면, 변경 사항이 발생했을 때 어느 부분을 수정해야 할지 혼란스러워질 수 있다.


  2. Repository의 JpaRepository < > 제네릭 타입

    JpaRepository의 제네릭 타입에는 두 가지 항목을 적어야 한다.
    첫 번째 항목은 "다룰 데이터의 종류(항상 Entity 타입이 와야 한다)" 두 번째 항목은 "Entity의 ID 필드의 타입"
    두 번째 항목의 타입은 Entity에서 설정한 ID 필드의 타입과 반드시 일치해야 한다.
    (Entity에서 필드란 Annotation이 붙은 곳을 말한다. @column 등..)


  3. 간단한 list 논리 흐름

    1) 사용자가 /member URL을 입력하면 public String Member 메서드가 실행된다.
    2) 이 메서드는 등록된 회원들의 정보를 보여주기 위해 MemberRepository를 이용해 데이터베이스와 연결한다.
    3) MemberRepository는 MemberEntity라는 데이터를 취급한다.
    4) findByNameContaining(keyword, pageable) 메서드를 호출하여, Page<MemberEntity> 타입의 값을 반환받는다.
    5) 반환된 값은 Page<MemberEntity> all에 담기고, 모델에 추가되어 뷰로 전송된다.


  4. Entity와 DTO의 변환

    Entity에서 DTO로 변환하는 것뿐만 아니라, DTO에서 Entity로 변환하는 것도 가능.
    두 가지 변환 모두 필요에 따라 사용할 수 있다.
    1) Entity에서 DTO로 변환
       목적: 데이터베이스에서 가져온 모든 정보를 담고 있는 Entity에서 클라이언트가 필요한 정보만 추출하여 DTO에 담아 전송할 때 사용해.
    2) DTO에서 Entity로 변환
       목적: 클라이언트에서 받은 데이터를 Entity로 변환하여 데이터베이스에 저장할 때 사용해. 클라이언트가 전송한 정보(예: 사용자 등록 정보)를 Entity 형태로 바꾸는 과정이야.


  5. Entity의 @ID 와 @GeneratedValue, @SequenceGenerator의 관계와 이해

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idx")
    위 코드는 JPA 내부에서 가상의 sequence를 생성하고, 이름을 "idx" 로 지어준다.
    (생성되는 가상 sequence의 증가량은 1로 설정되며, @SequenceGenerator 로 변경할 수 있다.)
    이 코드만으로는 데이터베이스와 연결할 수 없기 때문에 데이터베이스의 sequence에는 어떤 영향도 주지 못한다.

    @SequenceGenerator(name = "idx", sequenceName = "idx", allocationSize = 1)
    위 코드는 @GeneratedValue 에서 만든 가상 sequence를 고유 이름(generator = "idx")으로 인식해서 가져온다.
    그리고, 데이터베이스에 존재하는 실제 sequence 객체와 매칭하고, sequence의 증가량을 상세하게 설정한다.

    그러므로, @GeneratedValue로 가상의 sequence 를 생성하고
    @SequenceGenerator로 그 가상의 sequence 를 다듬어서 실제 데이터베이스에 적용시킨다고 할 수 있겠다.


  6. Long 과 long 의 관계
    Long은 null 값도 가질 수 있다는 점이 기본 타입인 long과의 차이점.
    Long은 정수 값을 나타내는 객체 타입이기 때문에 long 타입과 마찬가지로 숫자만 들어갈 수 있다.
    결국, Long 은 null 값을 가질 수 있는 long 타입이다.


  7. Interface의 시그니처
    "시그니처"는 메서드의 이름, 매개변수 목록(타입과 순서), 그리고 반환 타입을 포함한 정보를 의미합니다.
    예를 들어, List<PeopleEntity> findByNameContaining(String keyWord);에서
    findByNameContaining이 메서드 이름이고, String keyWord가 매개변수이며, List<PeopleEntity>가 반환 타입입니다.
    인터페이스에서는 이 시그니처만 정의하고, 실제 구현은 해당 인터페이스를 구현하는 클래스에서 제공됩니다.
    findByNameContaining 라는 코드를 실행시키면 알맞은 쿼리문을 자동으로 작성(실제 구현)한다.


  8. Class와 Interface의 관계와 특징
    C


  9. @Autowired와 @AllArgsConstructor의 차이

    1 -------------------------------------------------------------
    private PeopleRepository peopleRepository;

    @Autowired
    public MainController(PeopleRepository peopleRepository) {
    this.peopleRepository = peopleRepository;
    }

    2 -------------------------------------------------------------
    import lombok.AllArgsConstructor;

    @AllArgsConstructor
    public class MainController {

    private final PeopleRepository peopleRepository;
    private final AnotherService anotherService;
    }

    public MainController(PeopleRepository peopleRepository, AnotherService anotherService) {

    this.peopleRepository = peopleRepository;
    this.anotherService = anotherService;
    }
    위 생성자는 @AllArgsConstructor 가 자동으로 생성해준다, 눈에 보이지 않는다.

    @AllArgsConstructor를 사용하면 Lombok 덕분에 코드가 더 깔끔해진다.
    또, 생성자 매개변수로 모든 필드를 받아들이므로 의존성을 한 번에 주입할 수 있다.
    하지만 @Autowired를 사용하는 방식은 좀 더 명시적이고, 주입이 어떤 필드에 대한 것인지 쉽게 이해 가능.
    @Autowired 를 붙인 곳의 생성자가 자동으로 주입되며, 여러 개 사용 가능
    final 필드는 생성자에서만 초기화 가능: final 필드는 한 번 초기화되면 변경할 수 없고, 생성자에서만 초기화할 수 있다.
    final 을 사용하게 되면 첫 번째 생성자를 사용하지 않고 두 번째 생성자만 사용하고 싶을 때에도
    첫 번째 생성자에 @Autowired를 적어줘야 하기 때문에 불편하다.
    final 을 사용했을 때 모든 생성자에서 반드시 초기화 되어야 하는 이유는
    필드를 생성한 뒤 생성자로 값을 주거나 초기화 하지 않으면 필드는 빈껍데기가 된 채로 불변하게 되기 때문.

    final 필드: 생성자에서만 초기화할 수 있으며, 이후에는 값을 변경할 수 없습니다.
    비final 필드: 초기화 후에도 메서드나 다른 코드에서 값을 변경할 수 있습니다.


  10. List<PeopleEntity> people = new ArrayList<>()와 people = null;의 차이
  * 리스트 타입을 사용해야 .add() .remove() .size() 등의 메소드를 사용할 수 있다, 크기가 자동으로 조정된다. *

    1) people = new ArrayList<>() 를 적으면 메모리에 빈 리스트가 항상 존재하게 된다.
      이 코드에서 people은 ArrayList 객체이기도 하면서, List 타입으로 선언된 것이며,
      덕분에 유연하게 다양한 List 구현체를 사용할 수 있는 장점도 있다.
      하지만, 스위치가 계속 켜져있는 상태이기 때문에 메모리를 차지하게 되어 효율이 떨어진다.

      ex1) List<PeopleEntity> people = new ArrayList<>();

        PeopleEntity person1 = new PeopleEntity();
        person1.setId("user1");
        person1.setPwd("password1");
        person1.setName("Alice");
        person1.setAge(30);
        person1.setRegdate(new Date()); // 현재 날짜

        people.add(person1);
      이렇게 수동으로 데이터를 입력할 때 사용한다.

      ex2) List<String> cars = new ArrayList<>();

        // .add() 메소드를 사용하여 데이터 추가
        cars.add("Tesla Model S");
        cars.add("Ford Mustang");
        cars.add("Chevrolet Camaro");

        // 리스트 출력
        for (String car : cars) {
            System.out.println(car);
        }

    2) people = null 을 적으면 데이터가 들어오기 전에는 리스트가 아예 없는 상태이고, 메모리도 사용하지 않는다.
      따라서 people = peopleRepository.findAll() 등의 메서드를 호출할 때만 리스트가 생성되고 데이터가 들어온다.
      즉, 리스트가 필요한 순간에만 생성되기 때문에 효율이 높아진다.


  11. 오버로딩(overloading)과 오버라이딩(overriding)의 관계

    * 오버로딩(overloading)
    같은 이름의 메소드를 만들되, 매개변수의 타입이나 개수를 다르게 하여
    여러 가지 방식으로 호출할 수 있도록 하는 것이 오버로딩.
    ex)
    1. List<PeopleEntity> findByRegdate(int year);
    2. List<PeopleEntity> findByRegdate(int month);
    3. List<PeopleEntity> findByRegdate(int year, int month);

    적절한 예시지만 1번과 2번의 파라미터가 둘 다 int 타입이고 갯수도 같이 때문에 에러가 발생한다.
    하나로 합치거나 메서드의 이름을 다르게 해 줘야 한다.

    * 오버라이딩 된 메서드를 오버로딩 할 수도 있다.
    오버라이딩 된 메서드를 오버로딩한 메서드는 상위 클래스의 메서드와 매개변수가 다르기 때문에, 상위 메서드의 시그니처를 따르지 않는다.
    따라서 오버로딩 된 메서드는 상위 클래스의 메서드와 서로 다른 매개변수를 가질 수 있다.

    * 오버라이딩(overriding)
    상위 클래스의 메서드를 하위 클래스에서 재정의하는 것
    즉, 하위 클래스에서 상위 클래스의 메서드와 동일한 이름과 매개변수를 가진 메서드를 정의하여, 원래의 기능을 변경.
    extends 와 implements 중 하나를 해야 상위클래스의 메서드를 overriding 할 수 있다.
    ---------------------------------------------------------------------------------------------

    class Animal {
        void sound(String sound *매개변수가 반드시 있을 필요는 없다* ) {
            System.out.println("Animal makes a sound: " + sound);
        }
    }

    class Dog extends Animal {
        @Override
        void sound(String sound *매개변수가 반드시 있을 필요는 없다* ) {
            System.out.println("Dog barks: " + sound);
        }
    }



  12. DTO에 대해
    Entity의 문제: Entity를 사용하면 그 객체에 포함된 모든 정보(민감한 정보 등)가 클라이언트에 노출될 수 있다.
    Entity의 한계: Entity는 모든 필드를 포함하고 있기 때문에, 그 정보를 조작하거나 숨길 수 있는 방법이 제한적이다.
                  "정보를 조작하거나 숨길 수 있는 방법이 제한적" 라고 한 이유는 Entity를 사용해서
                  정보를 조작하거나 숨기는 과정은 까다롭고 실수가 발생할 가능성이 높아지기 때문이다.
                 (즉, Entity는 항상 모든 정보를 보여줄 수밖에 없다)
    DTO 사용 이유: DTO는 필요한 정보만 포함하므로, 보여주고 싶지 않은 데이터(예: 비밀번호, 나이 등)를 숨길 수 있다.

    DTO를 사용할 때 해당 DTO의 필드에 있는 모든 변수를 사용하지 않고 한 두가지를 빼고 싶을 때는
    Optional 타입을 사용하면 된다, 이는 값이 있을 수도 없을 수도 있는 경우를 처리할 때 유용하게 사용된다.

    데이터 구조화: DTO는 관련된 데이터를 하나의 객체로 묶어, 요청과 응답에서 필요한 데이터를 깔끔하게 관리할 수 있다.
    유지보수 용이: DTO를 사용하면, 데이터 구조를 한 곳에서 정의하고 수정할 수 있어, 유지보수가 쉬워진다.
    유효성 검사: DTO를 통해 입력값을 검증하고, 필수 항목이나 형식 등을 관리할 수 있다.
    코드의 일관성: DTO를 사용하면, 요청과 응답에서 데이터를 다루는 방식이 일관되게 유지되며, 코드의 가독성이 향상.
    재사용성: 같은 DTO를 여러 컨트롤러나 서비스에서 재사용할 수 있어, 코드 중복을 줄이고, 모듈화된 설계를 촉진.


  13. .stream() .map() .collect() .collect(Collectors.toList()); 에 대해
    stream = stream()을 호출하는 컬렉션의 항목(변수, 필드)을 꺼내서 쉽게 다룰 수 있게 해 준다.
    * 컬렉션의 타입 List, Set, Map 등..

    map = stream 으로 꺼낸 변수나 필드에 정보를 담게 해 준다.

    collect = map 처리가 끝났으면 다시 정리해서 호출하는 컬렉션의 타입에 맞게 정리해 준다.
    ex) .collect(Collectors.toList());

    * 재미있는 기능
    long count = people.stream()
        .filter(entity -> entity.getAge() > 18)
        .collect(Collectors.counting());


  14. @ModelAttribute과 바인딩에 대해
    @ModelAttribute: loginDTO 매개변수는 HTML 폼에서 전송된 ID와 비밀번호를 자동으로 LoginDTO 객체에 바인딩한다.
    위 Annotation 은 항상 DTO 매개변수 앞에 위치해야 인식된다.
    ex) public String setLogin(@ModelAttribute LoginDTO loginDTO, Model model) {

            return "login";
    }

    HTML 폼에서 입력한 데이터가 Java 객체의 필드와 연결되는 과정을 의미.
    HTML 폼의 <input> 태그에 지정한 name 속성과 LoginDTO의 필드명이 일치하면,
    Spring이 자동으로 해당 데이터를 가져와서 LoginDTO 객체에 설정한다.


  15. 리터럴이란?
    코드에서 고정된 값을 직접 나타내는 표현입니다. 예를 들어, 숫자 10이나 문자열 "안녕하세요"가 리터럴입니다.

    int number = 10; // 10은 정수 리터럴
    String greeting = "안녕하세요"; // "안녕하세요"는 문자열 리터럴

    여기서 10과 "안녕하세요"가 각각 리터럴이다.


  16. Spring의 의존성 주입
    @Controller
    public class MainController {

        private PeopleRepository peopleRepository;

        @Autowired
        public MainController(PeopleRepository parameter) {

            this.peopleRepository = parameter;
        }
    }
    위 코드를 살펴보자.
    private PeopleRepository peopleRepository; 이 코드는 PeopleRepository 타입의 변수를 선언한다.
    이 변수는 선언됐을 뿐, 아무 값도 가지고 있지 않다.

    Spring에서는 MainController 클래스의 메서드를 호출할 때 자동으로 MainController의 객체를 만들어 주고,
    객체를 만들 때 필요한 매개변수에 값도 자동으로 전달해 준다.


  17. Controller에서 필드와 생성자, 그리고 메서드의 논리 흐름

    Spring framework에서는 Application 실행 시 @Controller 등의 Annotation이 붙은 클래스들의 Instance가 자동 생성, 초기화 된다.
    이 파일에서 Controller 클래스를 초기화 시켜주는 생성자는 아래와 같다.
    public MainController(PeopleRepository parameter) {

        this.peopleRepository = parameter;
    }
    위 생성자로 클래스의 PeopleRepository peopleRepository 필드를 초기화 시켜 객체를 생성하는데
    PeopleRepository 타입인 매개변수 parameter를 받아 초기화 작업을 실행한다.
    생성자에 매개변수 부분 (PeopleRepository parameter) 에 데이터를 입력하지 않아도 자동으로
    PeopleRepository 파일을 찾아서 매개변수에 전달에 준다.
    따라서, 생성된 Controller 클래스의 객체는 PeopleRepository 의 모든 데이터를 가지고 있으며
    PeopleRepository의 JpaRepository 기능들도 모두 사용할 수 있게 된다.

    덕분에 "/list" 라는 url을 입력받아 getList() 메서드를 호출할 때,
    getList() 메서드 안에서 peopleRepository.findAll(); 과 같은 JpaRepository의 기능들을 사용할 수 있게 된다.


  18. Object 와 Instance
    Object가 더 넓은 개념으로, 메모리에 존재하는 모든 데이터를 포함.
    "클래스의 인스턴스"뿐만 아니라, 프로그램에서 다룰 수 있는 모든 것을 포괄하며,
    변수, 배열, 함수(일부 언어에서는 함수도 오브젝트로 취급) 등 모든 것이 오브젝트가 될 수 있다.

    Instance는 특정 클래스에서 생성된 "오브젝트"로, 클래스의 설계도에 따라 만들어진 구체적인 객체.
    때문에, Car class의 Instance인 myCar는 Car 클래스의 인스턴스이자 오브젝트이기도 해요.


  19. 바인딩(Binding)이란?
    클라이언트에서 전송된 데이터를 서버에서 사용하는 객체(예: DTO)로 변환하는 과정.
    일반적으로 두 가지 단계로 나뉜다.
    1) 요청 파라미터 수집: 클라이언트가 보낸 HTTP 요청의 파라미터를 수집.
    (요청 파라미터는 HTTP 요청을 통해 서버에 데이터를 전송할 때 사용되는 용어)
    2) 객체 매핑: 수집한 파라미터를 기반으로 DTO와 같은 객체에 값을 설정.


  20. 레코드: 데이터베이스에서 행(row)을 의미, 각 행은 여러 개의 컬럼(column)으로 구성


  21. 서비스 클래스(service class)와 비지니스 로직(business logic)의 관계
    Service class 에서 business logic을 정의하고
    controller에서 그 business logic을 호출하기 위해 service class을 사용한다.
    * Service logic이라는 용어는 없다, 서비스 클래스의 비즈니스 로직을 호출하는 코드만 있을 뿐이다.
      따라서, 서비스 클래스의 비즈니스 로직을 호출하는 코드를 Service logic 이라는 용어로 표현해서는 안 된다.


  22. 서비스 클래스의 생성 규칙
    보통은 컨트롤러에서 비슷한 기능을 하거나 로직을 공유하는 메서드들끼리 묶어서 서비스 클래스를 생성한다.
    하지만 꼭 그렇게 해야 한다는 규칙은 아니며, 컨트롤러의 메서드 하나 당 하나의 메서드를 생성해도 된다.

    1) 비슷한 기능을 수행하는 메서드 (아래의 코드처럼 말 그대로 기능이 매우 비슷할 때)
    2) 여러 메서드가 비슷한 비즈니스 로직을 공유할 때 (각 서비스 클래스는 하나의 책임을 가지도록 설계하는 것이 중요)
    로그인 메서드와 그에 대한 유효성 검사를 하는 메서드처럼, 서로 다른 메서드들이 같은 로직(예: 유효성 검사)을 사용하는 경우를 의미한다.

    public class UserService {

        // 사용자 가입
        public void registerUser(String username, String password) {
            // 회원가입 로직
            System.out.println("User " + username + " registered.");
        }

        // 사용자 로그인
        public boolean loginUser(String username, String password) {
            // 로그인 로직
            System.out.println("User " + username + " logged in.");
            return true; // 로그인 성공 예시
        }

        // 사용자 프로필 업데이트
        public void updateUserProfile(String username, String newProfile) {
            // 프로필 업데이트 로직
            System.out.println("User " + username + "'s profile updated to: " + newProfile);
        }
    }


  23. 스프링 부트 계층(Layer) 구조
    1) 프레젠테이션 계층
    HTTP 요청 응답하는 역할(Controller)

    2) 비지니스 ''
    비지니스 로직을 담당하는 역할(Service)

    3) 퍼시스턴스 계층 ''
    데이터베이스 로직을 담당하는 역할(Repository)


  24. 람다 표현식
    Java 8에서 도입된 기능으로 익명 함수(즉, 이름이 없는 함수)를 간결하게 표현하기 위한 문법입니다.
    이를 통해 코드의 간결성을 높이고, 특히 함수형 프로그래밍 스타일을 사용할 수 있게 합니다.
    람다식은 매개변수를 받아 코드 블록을 실행하는 형태로,
    반환 타입은 컴파일러가 자동으로 추론합니다.

    람다 표현식의 기본 형태
    (매개변수) -> { 실행할 코드 }
    1) 매개변수: 처리할 값을 받습니다.
    2) 화살표 (->): 람다 표현식의 구분자입니다.
    3) 실행할 코드: 실제로 실행될 코드가 들어갑니다, 여기에는 중괄호와 return을 사용할 수 있습니다.

    (1) 중괄호가 없을 때
    단일 실행문일 경우, 중괄호와 return을 생략할 수 있습니다.
    실행문이 한 줄이면, 그 값이 자동으로 반환됩니다.

    (매개변수) -> 실행할 코드  // 중괄호 없음, 자동으로 값 반환

    (2) 중괄호가 있을 때
    중괄호가 있으면 여러 줄의 코드가 포함될 수 있습니다.
    중괄호가 있는 경우, **명시적으로 return**을 사용해야 반환값이 있습니다.
    단, 반환값이 필요 없는 경우 return을 생략할 수 있습니다.

    (매개변수) -> {
        실행할 코드;
        return 값;  // 명시적 return 필요
    }

    중괄호와 return의 필요성 정리
    상황	            중괄호 사용    return 필요 여부

    단일 실행문	    중괄호 없음    return 생략 가능, 자동 반환
    여러 줄의 실행문	중괄호 있음    return이 필요 (반환값이 있을 경우)
    여러 줄의 실행문	중괄호 있음    return 없이 처리 가능 (반환값이 없을 경우)

  25.


  26.


  27.


  28.


  29.


  30.

*/