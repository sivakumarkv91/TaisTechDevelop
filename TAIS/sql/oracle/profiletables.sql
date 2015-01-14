CREATE TABLE tais_food_exp_settlement (
  settlement_id varchar2(254) NOT NULL,
  user_id varchar2(45) DEFAULT NULL,
  food_allowance_funds number(28,20) DEFAULT NULL,
  last_settlement_date date DEFAULT NULL,
  remarks varchar2(254) DEFAULT NULL,
  PRIMARY KEY (settlement_id)
) ;

CREATE TABLE tais_food_exp_users (
  settlement_id varchar2(254) NOT NULL,
  sequence_id number(10) NOT NULL,
  food_exp_user_id varchar2(254) DEFAULT NULL,
  PRIMARY KEY (settlement_id,sequence_id)
) ;

CREATE INDEX tais_food_exp_users_settlement_idx ON tais_food_exp_users (settlement_id,sequence_id);

CREATE TABLE tais_food_expenditure (
  id varchar2(254) NOT NULL,
  expense_date timestamp(0) DEFAULT NULL,
  expense_amount number(28,20) DEFAULT NULL,
  expense_status number(10) DEFAULT NULL,
  description varchar2(254) DEFAULT NULL,
  PRIMARY KEY (id)
) ;

CREATE TABLE tais_food_expense (
  user_id varchar2(254) NOT NULL,
  sequence_id number(10) NOT NULL,
  food_exp_item varchar2(254) DEFAULT NULL,
  PRIMARY KEY (user_id,sequence_id)
) ;

CREATE INDEX tais_food_expense_user_idx ON tais_food_expense (user_id,sequence_id);

CREATE TABLE tais_user (
  user_id varchar2(254) NOT NULL,
  employee_code varchar2(254) DEFAULT NULL,
  marital_status number(10) DEFAULT NULL,
  personal_phone varchar2(254) DEFAULT NULL,
  home_phone varchar2(254) DEFAULT NULL,
  job_location number(10) DEFAULT NULL,
  designation number(10) DEFAULT NULL,
  profile_photo_url varchar2(800) DEFAULT NULL,
  account_status number(10) DEFAULT NULL,
  iseligible_foodexpense number(3) DEFAULT NULL,
  is_admin number(3) DEFAULT NULL,
  food_exp_start_date timestamp(0) DEFAULT NULL,
  expense_status number(3) DEFAULT NULL,
  settled_expense_amount number(28,20) DEFAULT NULL,
  food_exp_end_date timestamp(0) DEFAULT NULL,
  PRIMARY KEY (user_id)
) ;

CREATE INDEX tais_user_user_idx ON tais_user (user_id);
CREATE TABLE tais_food_exp_settlement (
	settlement_id 		VARCHAR2(254)	NOT NULL,
	food_allowance_funds 	NUMBER(28, 20)	NULL,
	last_settlement_date 	DATE	NULL,
	remarks 		VARCHAR2(254)	NULL,
	PRIMARY KEY(settlement_id)
);

CREATE TABLE tais_food_exp_users (
	settlement_id 		VARCHAR2(254)	NOT NULL REFERENCES tais_food_exp_settlement(settlement_id),
	sequence_id 		INT	NOT NULL,
	food_exp_user_id 	VARCHAR(254)	NULL REFERENCES dps_user(id),
	PRIMARY KEY(settlement_id, sequence_id)
);

CREATE INDEX tais_food_exp_users_settlement_idx ON tais_food_exp_users(settlement_id, sequence_id);