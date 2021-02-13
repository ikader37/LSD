
--
-- TOC entry 3128 (class 0 OID 0)
-- Dependencies: 3
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON SCHEMA public IS 'standard public schema';


--
-- TOC entry 240 (class 1255 OID 33861)
-- Name: brancher(); Type: PROCEDURE; Schema: public; Owner: -
--

CREATE PROCEDURE public.brancher()
    LANGUAGE plpgsql
    AS $$
declare 
	idg integer;
	idp integer;
	ids record;
	ai integer;
	ta integer;
	pb record;
	bi integer;
begin
for idg in select idgroupe from groupe where idgroupe=3
	--ai=1;
	
	loop
	
			 
		select taille into ta from groupe where idgroupe=idg;
		for 
			ids in select idseance from seance where idgroupe =idg group by idseance
			
			 loop
			 bi=1;
			for pb in select idbranchement,idseance,idpatient from branchement where fg_passe='R' and idseance in (select idseance from seance where idgroupe=idg)
				 loop
					raise notice ' b';
					--ai=ai+1;
					bi=bi+1;
					update branchement set fg_passe='P' where idbranchement=pb.idbranchement;
					raise notice ' b %',bi;
				 end loop;
			 
			ai=1;
			if bi>1 then
				ai=bi;
			end if;
			 --Recueprons les patients reportes
			 
			 	for idp in select idpatient from patient where idgroupe =idg and idpatient not in (select idpatient from  branchement where fg_passe='R' and idseance in (select idseance from seance where idgroupe=idg) )
					loop
						--raise notice ' a %',ai;
						--raise notice 'P: %',idp;
						if ai<=ta then
							insert into branchement(idbranchement,idpatient,idseance,fg_passe) values(nextval('branchementsequence'),idp,ids.idseance,'P');
							commit;
						else
							insert into branchement(idbranchement,idpatient,idseance,fg_passe) values(nextval('branchementsequence'),idp,ids.idseance,'R');
							commit;
						end if;
						ai=ai+1;
						raise notice ' a %',ai;
					end loop;
				end loop;
	end loop;
end;
$$;


CREATE PROCEDURE public.brancher(datedeb character varying, datefin character varying)
    LANGUAGE plpgsql
    AS $$
declare 
	idg integer;
	idp integer;
	ids record;
	ai integer;
	ta integer;
begin
for idg in select idgroupe from groupe
	
	loop
	ai=0;
		select taille into ta from groupe where idgroupe=idg;
		for 
			ids in select idseance from seance where idgroupe =idg
			 loop
			 	for idp in select idpatient from patient where idgroupe =idg
					loop
						if ai<=ta then
							insert into branchement(idbranchement,idpatient,idseance,fg_passe) values(nextval('branchementsequence'),idp,ids.idseance,'P');
						else
							insert into branchement(idbranchement,idpatient,idseance,fg_passe) values(nextval('branchementsequence'),idp,ids.idseance,'R');
						end if;
						ai=ai+1;
					end loop;
				end loop;
	end loop;
end;
$$;

CREATE FUNCTION public.brancher(seance integer, groupe integer, hdb character varying, hfn character varying, OUT resut integer) RETURNS integer
    LANGUAGE plpgsql
    AS $$

DECLARE
	allp CURSOR FOR SELECT * from patient WHERE patient.idgroupe=groupe;
	chron CURSOR for select * from patient WHERE patient.idgroupe=groupe AND patient.etatsante="Chronique";
	unchron CURSOR for select * from patient WHERE patient.idgroupe=groupe AND patient.etatsante="Non Chronique";
	unitecur CURSOR for select * from unitedialyse;
BEGIN
   --Cherchons les patient selon le groupe
   
   --Ouvrons le curseur
   OPEN allp;
   OPEN chron;
   OPEN unchron;
   OPEN unitecur;
   --Parcouirons les unites de dialyse pour voir
   select idpatient from patient where idgroupe=groupe;
END;
$$;

CREATE PROCEDURE public.brancherauto(deb date, fin date)
    LANGUAGE plpgsql
    AS $$


declare 
	
	idp integer;
	--Type idjou is record(idjour integer);
	id_jour record;
	last_branch record;--stockera le dernier branchement d'un patient non fixe
	number_jour_courant integer;
	--Variable qui vont stocker les jours de la plages de date
	number_jour_deb integer;
	number_jour_fin integer;
	bon boolean;
	date_courant date;
	interval_jour_donnee record;-- C'est utilisé pour parcourir lùintervalle de dates données
	ids record;
	ai integer;
	ta integer;
	pb record;
	bi integer;
	today date;
	dateS varchar;
	curD date;
	jour_sean integer;
	number_jour integer;
	rec_groupe_seance record;
	rec_groupe record;
	string varchar(20);
	idg integer;
begin

		--Recuperons la date actuelle  sous postgreSQL en chaine de caracteres
		select to_char(now(),'dd-MM-yyyy') into dateS;
		--Convertissons en date de format dd-MM-yyyy
		today=to_date(dateS,'dd-MM-yyyy');
		
		
		
		select s.idseance as idseance,s.idjour as idjour into id_jour from seance s where s.idjour=0;
		
		
		
		
		--Recuperons les champs fg_fixe et intval_jour du groupe choisi
		--select fg_fixe,intval_jr into rec_groupe from groupe where idgroupe=idg;
		
		curD=deb;
		
		--exit when (curd > fin); 
		--Recuperons le numero du jour de  la semaine
		
		SELECT EXTRACT(isodow FROM curD) into number_jour;
		SELECT EXTRACT(isodow FROM fin) into number_jour_fin;
		SELECT EXTRACT(isodow FROM deb) into number_jour_deb;
		
		--Parcourons la liste des groupes
		
		for rec_groupe in select fg_fixe,intval_jr,idgroupe  from groupe
			loop
			idg=rec_groupe.idgroupe;
			--Cherchons a savoir si cest un groupe fixe ou  pas
			if rec_groupe.fg_fixe=true then
				--Cherchons toutes les seances du groupe et parcourons chacune

				for 
				  ids in select s.idseance as idseance,s.idjour as idjour,s.nombre_poste as nombre_poste,s.hdeb as hdeb,s.hfin as hfin  from seance s,seancegroupe sg where sg.idgroupe =idg and s.idseance=sg.idseance

				 loop
					bon=false;

				 --if id_jour.idjour==NULL then
				 --else
					jour_sean=LAST_VALUE(id_jour.idjour) over();
				--end if;

				--Parcourons les dates donnees pour voir si la seances courant a une date dans l'interval

				 --Exemple si la seance actuelle cest pour samedi et que dans la plagee de date ya pas samedi , on part a la seance suite
				 --for date_courant in select * from generate_series(deb,fin,interval '1 day')
					--loop
						SELECT EXTRACT(isodow FROM curD) into number_jour;
						raise notice 'FIN %',number_jour_fin;
						raise notice 'DEBUT %',number_jour_deb;
						raise notice 'JOUR :%',number_jour;
						if  ids.idjour <= number_jour_fin and number_jour_deb <= ids.idjour then
							--raise notice 'Trouver %',date_courant;
							bon=true;
							--string ='% days',ids.idjour-1;

							--parcourons la plage de date pour trouver celle qui correspond a celle si

							for date_courant in select * from generate_series(deb,fin,interval '1 day')
								loop

										SELECT EXTRACT(isodow FROM date_courant) into number_jour_courant;
										if number_jour_courant=ids.idjour then
											curD=date_courant;
										end if;
								end loop;
							--curD=deb +interval ids.idjour-1 || ' days';
						else
							continue;
						end if;
					--end loop;
					SELECT EXTRACT(isodow FROM curD) into number_jour;
					--Verifions si une date a ete trouvee pour cette seance
					--if bon=false then
					--	bon=false;
					--	curD=date (curD +interval '1 days');
					--	raise notice 'Continue ';
					--	continue;
					--end if;

				 raise notice 'SEANCE %:',LAST_VALUE(id_jour.idjour) over();
				 id_jour=ids;
				 bi=1;
				 --Verifions d'abord voir sil n'ya pas de reports deja enregistrés pour la seance actuelle
				  for pb in select idbranchement,idseance,idpatient from branchement where fg_passe='R'  and idseance=ids.idseance --in (select s.idseance as  idseance from seance s,seancegroupe sg where sg.idgroupe=idg and s.idseance=sg.idseance))
					 loop
						raise notice ' b';
						--ai=ai+1;
						--Verifions que le jour de la  seance actuelle est egale a la date courante
						if number_jour=id_jour.idjour then
							raise notice 'jour ok';
							update branchement set fg_passe='P' where idbranchement=pb.idbranchement;
							update branchement set idseance=ids.idseance where idbranchement=pb.idbranchement;
							update branchement set datebranch=curD where idbranchement=pb.idbranchement;
							update branchement set heuredebut=ids.hdeb where idbranchement=pb.idbranchement;
							update branchement set heurefin=ids.hfin where idbranchement=pb.idbranchement;
						end if;

						commit;
						bi=bi+1;

						raise notice ' b %',bi;
					 end loop;

				ai=1;
				if bi>1 then
					ai=bi;
				end if;
				 --Recueprons les patients non encore programmes pour cette seance reportes
				 --Evitons de brancher une personne 2 fois la meme date et la meme seance
					for idp in select pp.idpatient as idpatient from patient pp,seancepatient sp where pp.idpatient=sp.idpatient and sp.idseance=ids.idseance and pp.fg_sortie is false and pp.idpatient not in (select idpatient from  branchement where  datebranch=curD)
						loop
							raise notice ' a avant %',ai;
							--raise notice 'P: %',idp;
							--Assurons n		ous que la capacite de la seance n'est pas ateinte
							if ai<=id_jour.nombre_poste then

								if number_jour=id_jour.idjour then
									insert into branchement(idbranchement,idpatient,idseance,fg_passe,datebranch,heuredebut,heurefin) values(nextval('branchementsequence'),idp,ids.idseance,'P',curD,ids.hdeb,ids.hfin);
								end if;

								commit;
							else
								if number_jour=id_jour.idjour then
									insert into branchement(idbranchement,idpatient,idseance,fg_passe,datebranch,heuredebut,heurefin) values(nextval('branchementsequence'),idp,ids.idseance,'R',curD,ids.hdeb,ids.hfin);
								end if;
								commit;
							end if;
							ai=ai+1;
							raise notice ' a %',ai;
						end loop;
				--curD=date (curD +interval '1 days');
			end loop;

			else
			--Si le groupe en question n'est pas un groupe fixe
			--Pour chaque patient du  groupe,on cherche son derenier jour de branchement et on ajoute le nombre de joure de branchement pour faire le nouveau branchement
			--Cherchons la liste des patient de ce groupe
			for idp in select idpatient as idpatient from patient where idgroupe=idg
				loop
					--cherchons le dernier branchement de ce patient
					select max(idbranchement) as idd,datebranch as dat into last_branch from branchement where idpatient=idp group by datebranch;
					--verifions si la date est non nulle
					if last_branch.dat is not null then
						curD=last_branch.dat +(rec_groupe.intval_jr * interval'1 day');
					else --Si cest leur premierefois nous allons prendre la date de debut comme date de branchement
						raise notice 'FIRST';
						curD=deb;
						--insert into branchement(idbranchement,idpatient,fg_passe,datebranch) values(nextval('branchementsequence'),idp,'P',curD);
						raise notice 'BRANCHER';
					end if;

						--faisons une boucle pour pour parcourir toute la plage de date
						while deb <= curD and curD <= fin loop
							--Verifions si le patient courant n'a pas ete brancher a cette  meme date
							--select * from branchement where idpatient =ids and datebranc

							--Soyons sur que la date n'est pas un dimanche
							--Au cas ou cest dimanche, on ajoute un jour pour faire lundi
							SELECT EXTRACT(isodow FROM curD) into number_jour;

							if number_jour=7 then --
								curD=curD+ interval '1 days';
							end if;
							insert into branchement(idbranchement,idpatient,fg_passe,datebranch) values(nextval('branchementsequence'),idp,'P',curD);
							raise notice '2';
							raise notice 'COURS :%',curD;
							curD=curD+rec_groupe.intval_jr* interval '1 days';
						end loop;
						if deb <= curD and curD <= fin  then 

						else
							raise notice 'NOT DEDANS';
						end if;
					


				end loop;
			end if;
		end loop;
		--exit when(curD=fin);
		--end loop;
		
end;
$$;


CREATE PROCEDURE public.branchergroupe(idg integer)
    LANGUAGE plpgsql
    AS $$


declare 
	
	idp integer;
	--Type idjou is record(idjour integer);
	id_jour record;
	ids record;
	ai integer;
	ta integer;
	pb record;
	bi integer;
	today date;
	dateS varchar;
begin
		select to_char(now(),'dd-MM-yyyy') into dateS;
		today=to_date(dateS,'dd-MM-yyyy');
		select idseance,idjour into id_jour from seance where idjour=0;
		select taille into ta from groupe where idgroupe=idg;
		for 
			ids in select idseance,idjour from seance where idgroupe =idg
			
			 loop
			 
			 raise notice 'SEANCE %:',LAST_VALUE(id_jour.idjour) over();
			 id_jour=ids;
			 bi=1;
			  for pb in select idbranchement,idseance,idpatient from branchement where fg_passe='R'  and (idseance in (select idseance from seance where idgroupe=idg))
				 loop
					raise notice ' b';
					--ai=ai+1;
					
					update branchement set fg_passe='P' where idbranchement=pb.idbranchement;
					update branchement set idseance=ids.idseance where idbranchement=pb.idbranchement;
					--commit;
					bi=bi+1;
					
					raise notice ' b %',bi;
				 end loop;
			 
			ai=1;
			if bi>1 then
				ai=bi;
			end if;
			 --Recueprons les patients non encore programmes pour cette seance reportes
			 --Evitons de brancher un personne 2 fois dans la 
			 	for idp in select idpatient from patient where idgroupe =idg and idpatient not in (select idpatient from  branchement where  datebranch=today and (idseance in (select idseance from seance where idgroupe=idg)))
					loop
						raise notice ' a avant %',ai;
						--raise notice 'P: %',idp;
						if ai<=ta then
							insert into branchement(idbranchement,idpatient,idseance,fg_passe,datebranch) values(nextval('branchementsequence'),idp,ids.idseance,'P',today);
							--commit;
						else
							insert into branchement(idbranchement,idpatient,idseance,fg_passe,datebranch) values(nextval('branchementsequence'),idp,ids.idseance,'R',today);
							--commit;
						end if;
						ai=ai+1;
						raise notice ' a %',ai;
					end loop;
				end loop;

end;
$$;

CREATE PROCEDURE public.branchergroupe(idg integer, deb date, fin date)
    LANGUAGE plpgsql
    AS $$


declare 
	
	idp integer;
	--Type idjou is record(idjour integer);
	id_jour record;
	last_branch record;--stockera le dernier branchement d'un patient non fixe
	number_jour_courant integer;
	--Variable qui vont stocker les jours de la plages de date
	number_jour_deb integer;
	number_jour_fin integer;
	bon boolean;
	date_courant date;
	interval_jour_donnee record;-- C'est utilisé pour parcourir lùintervalle de dates données
	ids record;
	ai integer;
	ta integer;
	pb record;
	bi integer;
	today date;
	dateS varchar;
	curD date;
	jour_sean integer;
	number_jour integer;
	rec_groupe_seance record;
	rec_groupe record;
	string varchar(20);
begin

		--Recuperons la date actuelle  sous postgreSQL en chaine de caracteres
		select to_char(now(),'dd-MM-yyyy') into dateS;
		--Convertissons en date de format dd-MM-yyyy
		today=to_date(dateS,'dd-MM-yyyy');
		
		
		
		select s.idseance as idseance,s.idjour as idjour into id_jour from seance s where s.idjour=0;
		
		
		
		
		--Recuperons les champs fg_fixe et intval_jour du groupe choisi
		select fg_fixe,intval_jr into rec_groupe from groupe where idgroupe=idg;
		
		curD=deb;
		
		--exit when (curd > fin); 
		--Recuperons le numero du jour de  la semaine
		
		SELECT EXTRACT(isodow FROM curD) into number_jour;
		SELECT EXTRACT(isodow FROM fin) into number_jour_fin;
		SELECT EXTRACT(isodow FROM deb) into number_jour_deb;
		
		--Cherchons a savoir si cest un groupe fixe ou  pas
		if rec_groupe.fg_fixe=true then
			--Cherchons toutes les seances du groupe et parcourons chacune
			
			for 
			  ids in select s.idseance as idseance,s.idjour as idjour,s.nombre_poste as nombre_poste,s.hdeb as hdeb,s.hfin as hfin  from seance s,seancegroupe sg where sg.idgroupe =idg and s.idseance=sg.idseance
			
			 loop
			 	bon=false;
			
			 --if id_jour.idjour==NULL then
			 --else
			 	jour_sean=LAST_VALUE(id_jour.idjour) over();
			--end if;
			
			--Parcourons les dates donnees pour voir si la seances courant a une date dans l'interval
			 
			 --Exemple si la seance actuelle cest pour samedi et que dans la plagee de date ya pas samedi , on part a la seance suite
			 --for date_courant in select * from generate_series(deb,fin,interval '1 day')
			 	--loop
					SELECT EXTRACT(isodow FROM curD) into number_jour;
					raise notice 'FIN %',number_jour_fin;
					raise notice 'DEBUT %',number_jour_deb;
					raise notice 'JOUR :%',number_jour;
					if  ids.idjour <= number_jour_fin and number_jour_deb <= ids.idjour then
						--raise notice 'Trouver %',date_courant;
						bon=true;
						--string ='% days',ids.idjour-1;
						
						--parcourons la plage de date pour trouver celle qui correspond a celle si
						
						for date_courant in select * from generate_series(deb,fin,interval '1 day')
							loop
								
									SELECT EXTRACT(isodow FROM date_courant) into number_jour_courant;
									if number_jour_courant=ids.idjour then
										curD=date_courant;
									end if;
							end loop;
						--curD=deb +interval ids.idjour-1 || ' days';
					else
						continue;
					end if;
				--end loop;
				SELECT EXTRACT(isodow FROM curD) into number_jour;
				--Verifions si une date a ete trouvee pour cette seance
				--if bon=false then
				--	bon=false;
				--	curD=date (curD +interval '1 days');
				--	raise notice 'Continue ';
				--	continue;
				--end if;
			
			 raise notice 'SEANCE %:',LAST_VALUE(id_jour.idjour) over();
			 id_jour=ids;
			 bi=1;
			 --Verifions d'abord voir sil n'ya pas de reports deja enregistrés pour la seance actuelle
			  for pb in select idbranchement,idseance,idpatient from branchement where fg_passe='R'  and idseance=ids.idseance --in (select s.idseance as  idseance from seance s,seancegroupe sg where sg.idgroupe=idg and s.idseance=sg.idseance))
				 loop
					raise notice ' b';
					--ai=ai+1;
					--Verifions que le jour de la  seance actuelle est egale a la date courante
					if number_jour=id_jour.idjour then
						raise notice 'jour ok';
						update branchement set fg_passe='P' where idbranchement=pb.idbranchement;
						update branchement set idseance=ids.idseance where idbranchement=pb.idbranchement;
						update branchement set datebranch=curD where idbranchement=pb.idbranchement;
						update branchement set heuredebut=ids.hdeb where idbranchement=pb.idbranchement;
						update branchement set heurefin=ids.hfin where idbranchement=pb.idbranchement;
					end if;
					
					commit;
					bi=bi+1;
					
					raise notice ' b %',bi;
				 end loop;
			 
			ai=1;
			if bi>1 then
				ai=bi;
			end if;
			 --Recueprons les patients non encore programmes pour cette seance reportes
			 --Evitons de brancher une personne 2 fois la meme date et la meme seance
			 	for idp in select pp.idpatient as idpatient from patient pp,seancepatient sp where pp.idpatient=sp.idpatient and sp.idseance=ids.idseance and pp.fg_sortie is false and pp.idpatient not in (select idpatient from  branchement where  datebranch=curD)
					loop
						raise notice ' a avant %',ai;
						--raise notice 'P: %',idp;
						--Assurons n		ous que la capacite de la seance n'est pas ateinte
						if ai<=id_jour.nombre_poste then
						
							if number_jour=id_jour.idjour then
								insert into branchement(idbranchement,idpatient,idseance,fg_passe,datebranch,heuredebut,heurefin) values(nextval('branchementsequence'),idp,ids.idseance,'P',curD,ids.hdeb,ids.hfin);
							end if;
							
							commit;
						else
							if number_jour=id_jour.idjour then
								insert into branchement(idbranchement,idpatient,idseance,fg_passe,datebranch,heuredebut,heurefin) values(nextval('branchementsequence'),idp,ids.idseance,'R',curD,ids.hdeb,ids.hfin);
							end if;
							commit;
						end if;
						ai=ai+1;
						raise notice ' a %',ai;
					end loop;
			--curD=date (curD +interval '1 days');
		end loop;
		
		else
		--Si le groupe en question n'est pas un groupe fixe
		--Pour chaque patient du  groupe,on cherche son derenier jour de branchement et on ajoute le nombre de joure de branchement pour faire le nouveau branchement
		--Cherchons la liste des patient de ce groupe
		for idp in select idpatient as idpatient from patient where idgroupe=idg
			loop
				--cherchons le dernier branchement de ce patient
				select max(idbranchement) as idd,datebranch as dat into last_branch from branchement where idpatient=idp group by datebranch;
				--verifions si la date est non nulle
				if last_branch.dat is not null then
					curD=last_branch.dat +(rec_groupe.intval_jr * interval'1 day');
				else --Si cest leur premierefois nous allons prendre la date de debut comme date de branchement
					raise notice 'FIRST';
					curD=deb;
					--insert into branchement(idbranchement,idpatient,fg_passe,datebranch) values(nextval('branchementsequence'),idp,'P',curD);
					raise notice 'BRANCHER';
				end if;
					
					--faisons une boucle pour pour parcourir toute la plage de date
					while deb <= curD and curD <= fin loop
						--Verifions si le patient courant n'a pas ete brancher a cette  meme date
						--select * from branchement where idpatient =ids and datebranc
						
						--Soyons sur que la date n'est pas un dimanche
						--Au cas ou cest dimanche, on ajoute un jour pour faire lundi
						SELECT EXTRACT(isodow FROM curD) into number_jour;
						
						if number_jour=7 then --
							curD=curD+ interval '1 days';
						end if;
						insert into branchement(idbranchement,idpatient,fg_passe,datebranch) values(nextval('branchementsequence'),idp,'P',curD);
						raise notice '2';
						raise notice 'COURS :%',curD;
						curD=curD+rec_groupe.intval_jr* interval '1 days';
					end loop;
					if deb <= curD and curD <= fin  then 
						
					else
						raise notice 'NOT DEDANS';
					end if;
				
				
				
			end loop;
		end if;
		
		--exit when(curD=fin);
		--end loop;
		
end;
$$;


CREATE PROCEDURE public.branchergroupeseance(idg integer, id_s integer)
    LANGUAGE plpgsql
    AS $$
declare 
	
	idp integer;
	ids record;
	ai integer;
	ta integer;
	pb record;
	bi integer;
	today date;
	dateS varchar;
begin
		select to_char(now(),'dd-MM-yyyy') into dateS;
		today=to_date(dateS,'dd-MM-yyyy');
		 select idseance,hdeb,hfin into ids from seance where idgroupe =idg and idseance=id_s;
		 
		select taille into ta from groupe where idgroupe=idg;
			 bi=1;
			  for pb in select idbranchement,idseance,idpatient from branchement where fg_passe='R'  and idseance =id_s
				 loop
					raise notice ' b';
					--ai=ai+1;
					
					update branchement set fg_passe='P' where idbranchement=pb.idbranchement;
					commit;
					bi=bi+1;
					
					raise notice ' b %',bi;
				 end loop;
			 
			ai=1;
			if bi>1 then
				ai=bi;
			end if;
			 --Recueprons les patients non encore programmes pour cette seance reportes
			 --Evitons de brancher un personne 2 fois dans la 
			 	for idp in select idpatient from patient where idgroupe =idg and idpatient not in (select idpatient from  branchement where  datebranch=today and idseance=id_s)
					loop
						raise notice ' a avant %',ai;
						--raise notice 'P: %',idp;
						if ai<=ta then
							insert into branchement(idbranchement,idpatient,idseance,fg_passe,datebranch,heuredebut,heurefin) values(nextval('branchementsequence'),idp,id_s,'P',today,ids.hdeb,ids.hfin);
							commit;
						else
							insert into branchement(idbranchement,idpatient,idseance,fg_passe,datebranch,heuredebut,heurefin) values(nextval('branchementsequence'),idp,id_s,'R',today,ids.hdeb,ids.hfin);
							commit;
						end if;
						ai=ai+1;
						raise notice ' a %',ai;
					end loop;
				

end;
$$;


CREATE PROCEDURE public.branchergroupeseance(idg integer, id_sean integer, deb date, fin date)
    LANGUAGE plpgsql
    AS $$


declare 
	
	idp integer;
	--Type idjou is record(idjour integer);
	id_jour record;
	ids record;
	ai integer;
	ta integer;
	pb record;
	bi integer;
	today date;
	dateS varchar;
	curD date;
	jour_sean integer;
	number_jour integer;
	
begin
		select to_char(now(),'dd-MM-yyyy') into dateS;
		today=to_date(dateS,'dd-MM-yyyy');
		select idseance,idjour into id_jour from seance where idjour=0;
		select taille into ta from groupe where idgroupe=idg;
		curD=deb;
		loop
		exit when (curd > fin); 
		SELECT EXTRACT(isodow FROM curD) into number_jour;
		select idseance,idjour into ids from seance where idseance=id_sean;
		--id_jour=ids;
		
			 --if id_jour.idjour==NULL then
			 --else
			 	jour_sean=LAST_VALUE(id_jour.idjour) over();
			--end if;
			 raise notice 'SEANCE %:',LAST_VALUE(id_jour.idjour) over();
			 id_jour=ids;
			 bi=1;
			  for pb in select idbranchement,idseance,idpatient from branchement where fg_passe='R'  and (idseance in (select idseance from seance where idseance=id_sean))
				 loop
					raise notice ' b';
					--ai=ai+1;
					--Verifions que le jour de la  seance actuelle est egale a la date courante
					if number_jour=id_jour.idjour then
						raise notice 'jour ok';
						update branchement set fg_passe='P' where idbranchement=pb.idbranchement;
						update branchement set idseance=id_sean where idbranchement=pb.idbranchement;
						update branchement set datebranch=curD where idbranchement=pb.idbranchement;
					end if;
					
					--commit;
					bi=bi+1;
					
					raise notice ' b %',bi;
				 end loop;
			 
			ai=1;
			if bi>1 then
				ai=bi;
			end if;
			 --Recueprons les patients non encore programmes pour cette seance reportes
			 --Evitons de brancher un personne 2 fois dans la 
			 	for idp in select idpatient from patient where idgroupe =idg and idpatient not in (select idpatient from  branchement where  datebranch=curD and (idseance in (select idseance from seance where idseance=id_sean)))
					loop
						raise notice ' a avant %',ai;
						--raise notice 'P: %',idp;
						if ai<=ta then
						
							if number_jour=id_jour.idjour then
								insert into branchement(idbranchement,idpatient,idseance,fg_passe,datebranch) values(nextval('branchementsequence'),idp,id_sean,'P',curD);
							end if;
							
							--commit;
						else
							if number_jour=id_jour.idjour then
								insert into branchement(idbranchement,idpatient,idseance,fg_passe,datebranch) values(nextval('branchementsequence'),idp,id_sean,'R',curD);
							end if;
							--commit;
						end if;
						ai=ai+1;
						raise notice ' a %',ai;
					end loop;
					
			curD=date (curD +interval '1 days');
		end loop;

end;
$$;

CREATE PROCEDURE public.ff()
    LANGUAGE plpgsql
    AS $$
declare 
dd  varchar;
begin

dd='2019-09-29';
select (date '2019-09-29');
end;
$$;


CREATE FUNCTION public.sum_n_product(x integer, y integer, OUT sum integer, OUT prod integer) RETURNS record
    LANGUAGE plpgsql
    AS $$
BEGIN
    sum := x + y;
    prod := x * y;
END;
$$;

CREATE PROCEDURE public.tester(d date, e date)
    LANGUAGE plpgsql
    AS $$
declare
f date;
begin
for f in select * from generate_series(d,e,interval '1 day')
	loop
		raise notice 'DATE :%',f;
	end loop;
end;
$$;

CREATE PROCEDURE public.tester(d date, e date, h date)
    LANGUAGE plpgsql
    AS $$
declare
f date;
rec scroll cursor for select * from generate_series(d,e,interval '1 day');
begin

for f in select * from generate_series(d,e,interval '1 day')
	loop
		if f=h then
			raise notice 'TROUVER %',f;
			continue;
		end if;
		
		raise notice 'DATE :%',f;
	end loop;
	
end;
$$;


SET default_tablespace = '';

SET default_with_oids = false;


CREATE TABLE public.branchement (
    idhoraire integer,
    idpatient integer,
    idseance integer,
    idposte integer,
    libellebranchement character varying(50),
    datebranch date,
    etatbranch character varying(254),
    commentairemed character varying(254),
    prescription character varying(254),
    hospitalise boolean,
    idbranchement integer NOT NULL,
    heuredebut character varying,
    heurefin character varying,
    fg_passe character varying
);


ALTER TABLE public.branchement ALTER COLUMN idbranchement ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.branchement_idbranchement_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


CREATE SEQUENCE public.branchementsequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;



CREATE TABLE public.fonction (
    idfonction integer NOT NULL,
    libellefonction character varying(254)
);


CREATE SEQUENCE public.fonctionsequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;



CREATE SEQUENCE public.groupesequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 207 (class 1259 OID 16687)
-- Name: groupe; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.groupe (
    idgroupe integer DEFAULT nextval('public.groupesequence'::regclass) NOT NULL,
    libellegroupe character varying(254),
    nbseancesem integer,
    taille integer,
    fg_fixe boolean DEFAULT false,
    intval_jr integer
);


CREATE SEQUENCE public.groupepatientsequuence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;



CREATE TABLE public.horaire (
    idhoraire integer NOT NULL,
    libellehoraire character varying(254)
);


--
-- TOC entry 200 (class 1259 OID 16627)
-- Name: jour; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.jour (
    idjour integer NOT NULL,
    libellejour character varying(254)
);



CREATE SEQUENCE public.localitesequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


CREATE TABLE public.localite (
    idlocalite integer DEFAULT nextval('public.localitesequence'::regclass) NOT NULL,
    idpays integer,
    libellelocalite character varying(254),
    region character varying(254)
);



CREATE TABLE public.patient (
    idpatient integer NOT NULL,
    idfonction integer,
    idlocalite integer,
    nompatient character varying(254),
    prenompatient character varying(254),
    adresse character varying(254),
    telephone1 character varying(254),
    telephone2 character varying(254),
    accompagnant character varying(254),
    telephonaccompagnant character varying(254),
    antecedent character varying(254),
    dateentree date,
    nbreseance integer,
    etatsante character varying(254),
    contraintefonction character varying(254),
    sexe character varying,
    fg_sortie boolean DEFAULT false,
    taille_rein integer,
    dateentre date,
    datedebut date,
    cause_insuf integer,
    datesortie date,
    idgroupe integer,
    cause_sortie character varying(255)
);

CREATE SEQUENCE public.patientsequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


CREATE SEQUENCE public.payssequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


CREATE TABLE public.pays (
    idpays integer DEFAULT nextval('public.payssequence'::regclass) NOT NULL,
    libellepays character varying(254)
);



CREATE TABLE public.poste (
    idposte integer NOT NULL,
    idunite integer NOT NULL,
    libelleposte character varying(254),
    etatposte character varying(254),
    refconstructeur character varying(254)
);


CREATE SEQUENCE public.postesequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


CREATE TABLE public.profil (
    idprofil character varying NOT NULL,
    libelleprofil character varying,
    creele date,
    creepar character varying,
    modifierle date,
    modifiepar character varying
);



CREATE TABLE public.seance (
    idseance integer NOT NULL,
    idjour integer NOT NULL,
    libelleseance character varying(254),
    hdeb character varying,
    hfin character varying,
    idunite integer,
    nombre_poste integer
);


CREATE TABLE public.seancegroupe (
    idseance integer NOT NULL,
    idgroupe integer NOT NULL
);



CREATE TABLE public.seancepatient (
    idpatient integer NOT NULL,
    idseance integer NOT NULL
);



CREATE SEQUENCE public.seancesequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


CREATE TABLE public.unitedialyse (
    idunite integer NOT NULL,
    libelleunite character varying(254),
    nombreposte integer,
    categorie character varying(254)
);



CREATE SEQUENCE public.unitedialysesequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


CREATE TABLE public.utilisateur (
    iduser integer NOT NULL,
    loginuser character varying,
    loginpasswd character varying,
    connecte bigint,
    derniereconnexion date,
    creele date,
    creepar character varying,
    modifiepar character varying,
    idprofil character varying,
    modifiele character varying
);



INSERT INTO public.branchement VALUES (NULL, 27, 46, NULL, NULL, '2019-11-25', NULL, NULL, NULL, NULL, 196891, '6:0', '10:0', 'P');
INSERT INTO public.branchement VALUES (NULL, 28, 46, NULL, NULL, '2019-11-25', NULL, NULL, NULL, NULL, 196892, '6:0', '10:0', 'P');
INSERT INTO public.branchement VALUES (NULL, 29, 46, NULL, NULL, '2019-11-25', NULL, NULL, NULL, NULL, 196893, '6:0', '10:0', 'P');
INSERT INTO public.branchement VALUES (NULL, 30, 46, NULL, NULL, '2019-11-25', NULL, NULL, NULL, NULL, 196894, '6:0', '10:0', 'P');
INSERT INTO public.branchement VALUES (NULL, 31, 46, NULL, NULL, '2019-11-25', NULL, NULL, NULL, NULL, 196895, '6:0', '10:0', 'P');
INSERT INTO public.branchement VALUES (NULL, 32, 46, NULL, NULL, '2019-11-25', NULL, NULL, NULL, NULL, 196896, '6:0', '10:0', 'P');
INSERT INTO public.branchement VALUES (NULL, 35, 46, NULL, NULL, '2019-11-25', NULL, NULL, NULL, NULL, 196897, '6:0', '10:0', 'P');
INSERT INTO public.branchement VALUES (NULL, 36, 46, NULL, NULL, '2019-11-25', NULL, NULL, NULL, NULL, 196898, '6:0', '10:0', 'P');
INSERT INTO public.branchement VALUES (NULL, 37, 46, NULL, NULL, '2019-11-25', NULL, NULL, NULL, NULL, 196899, '6:0', '10:0', 'P');
INSERT INTO public.branchement VALUES (NULL, 43, 51, NULL, NULL, '2019-11-20', NULL, NULL, NULL, NULL, 196949, '6:0', '9:0', 'P');
INSERT INTO public.branchement VALUES (NULL, 48, 46, NULL, NULL, '2019-11-18', NULL, NULL, NULL, NULL, 196951, '6:0', '10:0', 'P');
INSERT INTO public.branchement VALUES (NULL, 48, 51, NULL, NULL, '2019-11-20', NULL, NULL, NULL, NULL, 196952, '6:0', '9:0', 'P');
INSERT INTO public.branchement VALUES (NULL, 39, 46, NULL, NULL, '2019-11-18', NULL, NULL, NULL, NULL, 196930, '6:0', '10:0', 'P');
INSERT INTO public.branchement VALUES (NULL, 38, 46, NULL, NULL, '2019-11-25', NULL, NULL, NULL, NULL, 196900, '6:0', '10:0', 'P');
INSERT INTO public.branchement VALUES (NULL, 26, 46, NULL, NULL, '2019-11-25', NULL, NULL, NULL, NULL, 196890, '6:0', '10:0', 'R');
INSERT INTO public.branchement VALUES (NULL, 39, 46, NULL, NULL, '2019-11-25', NULL, NULL, NULL, NULL, 196901, '6:0', '10:0', 'P');
INSERT INTO public.branchement VALUES (NULL, 40, 46, NULL, NULL, '2019-11-18', NULL, NULL, NULL, NULL, 196931, '6:0', '10:0', 'P');
INSERT INTO public.branchement VALUES (NULL, 40, 46, NULL, NULL, '2019-11-25', NULL, NULL, NULL, NULL, 196902, '6:0', '10:0', 'P');
INSERT INTO public.branchement VALUES (NULL, 41, 46, NULL, NULL, '2019-11-18', NULL, NULL, NULL, NULL, 196932, '6:0', '10:0', 'P');
INSERT INTO public.branchement VALUES (NULL, 41, 46, NULL, NULL, '2019-11-25', NULL, NULL, NULL, NULL, 196903, '6:0', '10:0', 'P');
INSERT INTO public.branchement VALUES (NULL, 42, 46, NULL, NULL, '2019-11-18', NULL, NULL, NULL, NULL, 196933, '6:0', '10:0', 'P');
INSERT INTO public.branchement VALUES (NULL, 42, 46, NULL, NULL, '2019-11-25', NULL, NULL, NULL, NULL, 196904, '6:0', '10:0', 'P');
INSERT INTO public.branchement VALUES (NULL, 43, 46, NULL, NULL, '2019-11-18', NULL, NULL, NULL, NULL, 196934, '6:0', '10:0', 'P');
INSERT INTO public.branchement VALUES (NULL, 43, 46, NULL, NULL, '2019-11-25', NULL, NULL, NULL, NULL, 196905, '6:0', '10:0', 'P');
INSERT INTO public.branchement VALUES (NULL, 44, 46, NULL, NULL, '2019-11-25', NULL, NULL, NULL, NULL, 196906, '6:0', '10:0', 'P');
INSERT INTO public.branchement VALUES (NULL, 45, NULL, NULL, NULL, '2019-11-18', NULL, NULL, NULL, NULL, 196907, NULL, NULL, 'P');
INSERT INTO public.branchement VALUES (NULL, 45, NULL, NULL, NULL, '2019-11-20', NULL, NULL, NULL, NULL, 196908, NULL, NULL, 'P');
INSERT INTO public.branchement VALUES (NULL, 45, NULL, NULL, NULL, '2019-11-22', NULL, NULL, NULL, NULL, 196909, NULL, NULL, 'P');
INSERT INTO public.branchement VALUES (NULL, 45, NULL, NULL, NULL, '2019-11-25', NULL, NULL, NULL, NULL, 196910, NULL, NULL, 'P');
INSERT INTO public.branchement VALUES (NULL, 46, NULL, NULL, NULL, '2019-11-18', NULL, NULL, NULL, NULL, 196911, NULL, NULL, 'P');
INSERT INTO public.branchement VALUES (NULL, 46, NULL, NULL, NULL, '2019-11-20', NULL, NULL, NULL, NULL, 196912, NULL, NULL, 'P');
INSERT INTO public.branchement VALUES (NULL, 46, NULL, NULL, NULL, '2019-11-22', NULL, NULL, NULL, NULL, 196913, NULL, NULL, 'P');
INSERT INTO public.branchement VALUES (NULL, 46, NULL, NULL, NULL, '2019-11-25', NULL, NULL, NULL, NULL, 196914, NULL, NULL, 'P');
INSERT INTO public.branchement VALUES (NULL, 47, NULL, NULL, NULL, '2019-11-18', NULL, NULL, NULL, NULL, 196915, NULL, NULL, 'P');
INSERT INTO public.branchement VALUES (NULL, 47, NULL, NULL, NULL, '2019-11-20', NULL, NULL, NULL, NULL, 196916, NULL, NULL, 'P');
INSERT INTO public.branchement VALUES (NULL, 47, NULL, NULL, NULL, '2019-11-22', NULL, NULL, NULL, NULL, 196917, NULL, NULL, 'P');
INSERT INTO public.branchement VALUES (NULL, 47, NULL, NULL, NULL, '2019-11-25', NULL, NULL, NULL, NULL, 196918, NULL, NULL, 'P');
INSERT INTO public.branchement VALUES (NULL, 26, 46, NULL, NULL, '2019-11-18', NULL, NULL, NULL, NULL, 196919, '6:0', '10:0', 'P');
INSERT INTO public.branchement VALUES (NULL, 27, 46, NULL, NULL, '2019-11-18', NULL, NULL, NULL, NULL, 196920, '6:0', '10:0', 'P');
INSERT INTO public.branchement VALUES (NULL, 28, 46, NULL, NULL, '2019-11-18', NULL, NULL, NULL, NULL, 196921, '6:0', '10:0', 'P');
INSERT INTO public.branchement VALUES (NULL, 29, 46, NULL, NULL, '2019-11-18', NULL, NULL, NULL, NULL, 196922, '6:0', '10:0', 'P');
INSERT INTO public.branchement VALUES (NULL, 30, 46, NULL, NULL, '2019-11-18', NULL, NULL, NULL, NULL, 196923, '6:0', '10:0', 'P');
INSERT INTO public.branchement VALUES (NULL, 31, 46, NULL, NULL, '2019-11-18', NULL, NULL, NULL, NULL, 196924, '6:0', '10:0', 'P');
INSERT INTO public.branchement VALUES (NULL, 32, 46, NULL, NULL, '2019-11-18', NULL, NULL, NULL, NULL, 196925, '6:0', '10:0', 'P');
INSERT INTO public.branchement VALUES (NULL, 35, 46, NULL, NULL, '2019-11-18', NULL, NULL, NULL, NULL, 196926, '6:0', '10:0', 'P');
INSERT INTO public.branchement VALUES (NULL, 36, 46, NULL, NULL, '2019-11-18', NULL, NULL, NULL, NULL, 196927, '6:0', '10:0', 'P');
INSERT INTO public.branchement VALUES (NULL, 37, 46, NULL, NULL, '2019-11-18', NULL, NULL, NULL, NULL, 196928, '6:0', '10:0', 'P');
INSERT INTO public.branchement VALUES (NULL, 28, 51, NULL, NULL, '2019-11-20', NULL, NULL, NULL, NULL, 196936, '6:0', '9:0', 'P');
INSERT INTO public.branchement VALUES (NULL, 29, 51, NULL, NULL, '2019-11-20', NULL, NULL, NULL, NULL, 196937, '6:0', '9:0', 'P');
INSERT INTO public.branchement VALUES (NULL, 30, 51, NULL, NULL, '2019-11-20', NULL, NULL, NULL, NULL, 196938, '6:0', '9:0', 'P');
INSERT INTO public.branchement VALUES (NULL, 31, 51, NULL, NULL, '2019-11-20', NULL, NULL, NULL, NULL, 196939, '6:0', '9:0', 'P');
INSERT INTO public.branchement VALUES (NULL, 32, 51, NULL, NULL, '2019-11-20', NULL, NULL, NULL, NULL, 196940, '6:0', '9:0', 'P');
INSERT INTO public.branchement VALUES (NULL, 35, 51, NULL, NULL, '2019-11-20', NULL, NULL, NULL, NULL, 196941, '6:0', '9:0', 'P');
INSERT INTO public.branchement VALUES (NULL, 36, 51, NULL, NULL, '2019-11-20', NULL, NULL, NULL, NULL, 196942, '6:0', '9:0', 'P');
INSERT INTO public.branchement VALUES (NULL, 37, 51, NULL, NULL, '2019-11-20', NULL, NULL, NULL, NULL, 196943, '6:0', '9:0', 'P');
INSERT INTO public.branchement VALUES (NULL, 38, 51, NULL, NULL, '2019-11-20', NULL, NULL, NULL, NULL, 196944, '6:0', '9:0', 'P');
INSERT INTO public.branchement VALUES (NULL, 39, 51, NULL, NULL, '2019-11-20', NULL, NULL, NULL, NULL, 196945, '6:0', '9:0', 'P');
INSERT INTO public.branchement VALUES (NULL, 44, 54, NULL, NULL, '2019-11-20', NULL, NULL, NULL, NULL, 196950, '9:0', '12:0', 'P');
INSERT INTO public.branchement VALUES (NULL, 44, 46, NULL, NULL, '2019-11-18', NULL, NULL, NULL, NULL, 196935, '6:0', '10:0', 'P');
INSERT INTO public.branchement VALUES (NULL, 38, 46, NULL, NULL, '2019-11-18', NULL, NULL, NULL, NULL, 196929, '6:0', '10:0', 'P');
INSERT INTO public.branchement VALUES (NULL, 40, 51, NULL, NULL, '2019-11-20', NULL, NULL, NULL, NULL, 196946, '6:0', '9:0', 'P');
INSERT INTO public.branchement VALUES (NULL, 41, 51, NULL, NULL, '2019-11-20', NULL, NULL, NULL, NULL, 196947, '6:0', '9:0', 'P');
INSERT INTO public.branchement VALUES (NULL, 42, 51, NULL, NULL, '2019-11-20', NULL, NULL, NULL, NULL, 196948, '6:0', '9:0', 'P');



INSERT INTO public.fonction VALUES (24, 'fonction 1');
INSERT INTO public.fonction VALUES (25, 'ffonction 2');
INSERT INTO public.fonction VALUES (36, 'Commerçant');
INSERT INTO public.fonction VALUES (40, 'Fonctionnaire');



INSERT INTO public.groupe VALUES (18, 'groupeLuMerVend', NULL, NULL, true, NULL);
INSERT INTO public.groupe VALUES (19, 'GroupeLMerSam', NULL, NULL, true, NULL);
INSERT INTO public.groupe VALUES (20, 'GroupeMarJeudiSamedi', NULL, NULL, true, NULL);
INSERT INTO public.groupe VALUES (21, 'groupeLJe', NULL, NULL, true, NULL);
INSERT INTO public.groupe VALUES (22, 'GroupeLVen', 1, 30, true, NULL);
INSERT INTO public.groupe VALUES (23, 'GroupeMarVen', NULL, NULL, true, NULL);
INSERT INTO public.groupe VALUES (24, 'GroupeMarSam', NULL, NULL, true, NULL);
INSERT INTO public.groupe VALUES (25, 'GroupeMercSam', NULL, NULL, true, NULL);
INSERT INTO public.groupe VALUES (26, 'groupefixe2', 3, 111, false, 2);
INSERT INTO public.groupe VALUES (28, 'groupe', 2, 3, false, 3);



INSERT INTO public.jour VALUES (1, 'Lundi');
INSERT INTO public.jour VALUES (2, 'Mardi');
INSERT INTO public.jour VALUES (3, 'Mercredi');
INSERT INTO public.jour VALUES (4, 'Jeudi');
INSERT INTO public.jour VALUES (5, 'Vendredi');
INSERT INTO public.jour VALUES (6, 'Samedi');
INSERT INTO public.jour VALUES (7, 'Dimanche');

INSERT INTO public.localite VALUES (7, NULL, 'localite 1', '');
INSERT INTO public.localite VALUES (8, NULL, 'Ouagadougou', NULL);
INSERT INTO public.localite VALUES (9, NULL, 'Bobo', NULL);


INSERT INTO public.patient VALUES (34, 24, 7, 'nom7', 'prenom7', '', '', '', '', '', '', '2019-11-07', NULL, 'Non chronique', '', 'M', true, NULL, NULL, NULL, NULL, '2019-11-19', 18, 'comment');
INSERT INTO public.patient VALUES (33, 24, 7, 'nom6', 'prenom6', '', '', '', '', '', '', '2019-11-07', NULL, 'Non chronique', '', 'M', true, NULL, NULL, NULL, NULL, '2019-11-20', 18, 'hjhhhj');
INSERT INTO public.patient VALUES (25, 24, 7, 'qweqweqww', 'weqweqw', 'wqeqweqw', '', '', 'asdd', '12-13-11-12', 'asdas', '2019-11-01', NULL, 'Non chronique', 'qweqw', 'M', false, NULL, NULL, NULL, NULL, NULL, 18, NULL);
INSERT INTO public.patient VALUES (26, 24, 7, 'asdasd', 'dqwqw', 'qwqwe', '', '', 'sdqweqw', '12-12-12-12', 'qweqw', '2019-11-01', NULL, 'Non chronique', 'asdas', 'M', false, NULL, NULL, NULL, NULL, '2019-11-06', 18, '');
INSERT INTO public.patient VALUES (27, 40, 8, 'ouedraogo', 'PRENOM 1', '', '23-24-32-44', '12-32-11-34', 'sadas', '23-23-21-21', 'adasd', '2019-11-07', NULL, 'Non chronique', 'contrainte', 'M', false, NULL, NULL, NULL, NULL, NULL, 19, NULL);
INSERT INTO public.patient VALUES (28, 40, 8, 'nom1', 'prenom', 'adresse', '12-32-34-55', '22-13-54-54', '', '', '', '2019-11-07', 3, 'Non chronique', 'contraintes', 'M', false, NULL, NULL, NULL, NULL, NULL, 18, NULL);
INSERT INTO public.patient VALUES (29, 24, 8, 'nom2', 'nom2', '', '', '', '', '', '', '2019-11-07', NULL, 'Non chronique', '', 'M', false, NULL, NULL, NULL, NULL, NULL, 18, NULL);
INSERT INTO public.patient VALUES (30, 24, 8, 'nom3', 'nom3', '', '', '', '', '', '', '2019-11-07', NULL, 'Non chronique', '', 'M', false, NULL, NULL, NULL, NULL, NULL, 18, NULL);
INSERT INTO public.patient VALUES (31, 36, 7, 'nom4', 'prenom4', '', '', '', '', '', '', NULL, NULL, 'Non chronique', '', 'M', false, NULL, NULL, NULL, NULL, NULL, 18, NULL);
INSERT INTO public.patient VALUES (32, 24, 7, 'nom5', 'prenom5', '', '', '', '', '', '', '2019-11-07', NULL, 'Non chronique', '', 'M', false, NULL, NULL, NULL, NULL, NULL, 18, NULL);
INSERT INTO public.patient VALUES (35, 24, 7, 'nom8', 'prenom8', '', '', '', '', '', '', '2019-11-07', NULL, 'Non chronique', '', 'M', false, NULL, NULL, NULL, NULL, NULL, 18, NULL);
INSERT INTO public.patient VALUES (36, 24, 7, 'nom9', 'prenom9', '', '23-42-44-54', '', '', '', '', '2019-11-07', NULL, 'Non chronique', '', 'M', false, NULL, NULL, NULL, NULL, NULL, 18, NULL);
INSERT INTO public.patient VALUES (37, 24, 7, 'nom10', 'prenom10', '', '', '', '', '', '', '2019-11-07', NULL, 'Non chronique', '', 'M', false, NULL, NULL, NULL, NULL, NULL, 18, NULL);
INSERT INTO public.patient VALUES (38, 24, 7, 'nom12', 'prenom12', '', '', '', '', '', '', '2019-11-07', NULL, 'Non chronique', '', 'M', false, NULL, NULL, NULL, NULL, NULL, 18, NULL);
INSERT INTO public.patient VALUES (39, 24, 7, 'nom13', 'prenom13', '', '', '', '', '', '', '2019-11-07', NULL, 'Non chronique', '', 'M', false, NULL, NULL, NULL, NULL, NULL, 18, NULL);
INSERT INTO public.patient VALUES (40, 24, 7, 'nom14', 'prenom14', '', '', '', '', '', '', '2019-11-07', NULL, 'Non chronique', '', 'F', false, NULL, NULL, NULL, NULL, NULL, 18, NULL);
INSERT INTO public.patient VALUES (41, 24, 7, 'nom15', 'prenom15', '', '', '', '', '', '', '2019-11-07', NULL, 'Non chronique', '', 'M', false, NULL, NULL, NULL, NULL, NULL, 18, NULL);
INSERT INTO public.patient VALUES (42, 24, 7, 'nom16', 'prenom16', '', '', '', '', '', '', '2019-11-07', NULL, 'Non chronique', '', 'M', false, NULL, NULL, NULL, NULL, NULL, 18, NULL);
INSERT INTO public.patient VALUES (43, 24, 7, 'ghgghhg', 'qqwqw', '', '', '', '', '', '', '2019-11-07', NULL, 'Non chronique', '', 'M', false, NULL, NULL, NULL, NULL, NULL, 18, NULL);
INSERT INTO public.patient VALUES (44, 24, 7, 'hhjhjjh', '', '', '', '', '', '', '', '2019-11-07', NULL, 'Non chronique', '', 'M', false, NULL, NULL, NULL, NULL, NULL, 18, NULL);
INSERT INTO public.patient VALUES (45, 24, 7, 'nom4', '121', 'ppp', '', '', '', '', '', NULL, NULL, 'Non chronique', '', 'M', false, NULL, NULL, NULL, NULL, NULL, 26, NULL);
INSERT INTO public.patient VALUES (46, 24, 7, 'nom5', 'ewhrhwehrj', '', '', '', '', '', 'dsfsd', NULL, NULL, 'Non chronique', '', 'M', false, NULL, NULL, NULL, NULL, NULL, 26, NULL);
INSERT INTO public.patient VALUES (47, 24, 7, 'sdfsd', 'dfwe', '', '', '', '', '', '', NULL, NULL, 'Non chronique', '', 'M', false, NULL, NULL, NULL, NULL, NULL, 26, NULL);
INSERT INTO public.patient VALUES (48, 24, 7, 'nom90', 'prenom', '', '', '', '', '', '', '2019-11-19', NULL, 'Non chronique', '', 'M', false, NULL, NULL, NULL, NULL, NULL, 19, NULL);




INSERT INTO public.poste VALUES (8, 41, 'poste 1', 'Fonctionnel', '000999');


INSERT INTO public.profil VALUES ('2', 'secretaire', NULL, NULL, NULL, NULL);
INSERT INTO public.profil VALUES ('3', 'medecin', NULL, NULL, NULL, NULL);
INSERT INTO public.profil VALUES ('1', 'administrateur', NULL, NULL, NULL, NULL);



INSERT INTO public.seance VALUES (46, 1, 'seanceL6_10', '6:0', '10:0', 27, 10);
INSERT INTO public.seance VALUES (47, 1, 'seanceL10-14', '10:0', '14:0', 27, 10);
INSERT INTO public.seance VALUES (48, 1, 'seanceL14_18', '14:0', '18:0', 27, 10);
INSERT INTO public.seance VALUES (49, 2, 'SeanceMar6_10', '6:0', '10:0', 27, 10);
INSERT INTO public.seance VALUES (50, 2, 'seanceMar10_14', '10:0', '14:0', 27, 10);
INSERT INTO public.seance VALUES (55, 1, 'seanceL', '14:0', '16:0', 27, 216);
INSERT INTO public.seance VALUES (51, 3, 'seanceMer6_9', '6:0', '9:0', 27, 10);
INSERT INTO public.seance VALUES (54, 3, 'seanceMer9_12', '9:0', '12:0', 27, 10);
INSERT INTO public.seance VALUES (53, 3, 'seanceMer14_18', '14:0', '18:0', 27, 10);



INSERT INTO public.seancegroupe VALUES (46, 18);
INSERT INTO public.seancegroupe VALUES (46, 19);
INSERT INTO public.seancegroupe VALUES (46, 21);
INSERT INTO public.seancegroupe VALUES (46, 22);
INSERT INTO public.seancegroupe VALUES (47, 18);
INSERT INTO public.seancegroupe VALUES (47, 19);
INSERT INTO public.seancegroupe VALUES (47, 21);
INSERT INTO public.seancegroupe VALUES (47, 22);
INSERT INTO public.seancegroupe VALUES (48, 18);
INSERT INTO public.seancegroupe VALUES (48, 19);
INSERT INTO public.seancegroupe VALUES (48, 21);
INSERT INTO public.seancegroupe VALUES (48, 22);
INSERT INTO public.seancegroupe VALUES (49, 20);
INSERT INTO public.seancegroupe VALUES (49, 23);
INSERT INTO public.seancegroupe VALUES (49, 24);
INSERT INTO public.seancegroupe VALUES (50, 20);
INSERT INTO public.seancegroupe VALUES (50, 23);
INSERT INTO public.seancegroupe VALUES (50, 24);
INSERT INTO public.seancegroupe VALUES (51, 18);
INSERT INTO public.seancegroupe VALUES (51, 19);
INSERT INTO public.seancegroupe VALUES (53, 18);
INSERT INTO public.seancegroupe VALUES (53, 19);
INSERT INTO public.seancegroupe VALUES (53, 25);
INSERT INTO public.seancegroupe VALUES (54, 18);
INSERT INTO public.seancegroupe VALUES (54, 19);
INSERT INTO public.seancegroupe VALUES (54, 25);
INSERT INTO public.seancegroupe VALUES (55, 18);
INSERT INTO public.seancegroupe VALUES (55, 19);
INSERT INTO public.seancegroupe VALUES (55, 21);
INSERT INTO public.seancegroupe VALUES (55, 22);



INSERT INTO public.seancepatient VALUES (26, 46);
INSERT INTO public.seancepatient VALUES (26, 48);
INSERT INTO public.seancepatient VALUES (27, 46);
INSERT INTO public.seancepatient VALUES (28, 46);
INSERT INTO public.seancepatient VALUES (28, 51);
INSERT INTO public.seancepatient VALUES (29, 46);
INSERT INTO public.seancepatient VALUES (29, 51);
INSERT INTO public.seancepatient VALUES (30, 46);
INSERT INTO public.seancepatient VALUES (30, 51);
INSERT INTO public.seancepatient VALUES (31, 46);
INSERT INTO public.seancepatient VALUES (31, 51);
INSERT INTO public.seancepatient VALUES (32, 46);
INSERT INTO public.seancepatient VALUES (32, 51);
INSERT INTO public.seancepatient VALUES (33, 46);
INSERT INTO public.seancepatient VALUES (33, 51);
INSERT INTO public.seancepatient VALUES (34, 46);
INSERT INTO public.seancepatient VALUES (34, 51);
INSERT INTO public.seancepatient VALUES (35, 46);
INSERT INTO public.seancepatient VALUES (35, 51);
INSERT INTO public.seancepatient VALUES (36, 46);
INSERT INTO public.seancepatient VALUES (36, 51);
INSERT INTO public.seancepatient VALUES (37, 46);
INSERT INTO public.seancepatient VALUES (37, 51);
INSERT INTO public.seancepatient VALUES (38, 46);
INSERT INTO public.seancepatient VALUES (38, 51);
INSERT INTO public.seancepatient VALUES (39, 46);
INSERT INTO public.seancepatient VALUES (39, 51);
INSERT INTO public.seancepatient VALUES (40, 46);
INSERT INTO public.seancepatient VALUES (40, 51);
INSERT INTO public.seancepatient VALUES (41, 46);
INSERT INTO public.seancepatient VALUES (41, 51);
INSERT INTO public.seancepatient VALUES (42, 46);
INSERT INTO public.seancepatient VALUES (42, 51);
INSERT INTO public.seancepatient VALUES (43, 46);
INSERT INTO public.seancepatient VALUES (43, 51);
INSERT INTO public.seancepatient VALUES (44, 46);
INSERT INTO public.seancepatient VALUES (44, 54);
INSERT INTO public.seancepatient VALUES (48, 46);
INSERT INTO public.seancepatient VALUES (48, 51);




INSERT INTO public.unitedialyse VALUES (27, 'unite 2', 5, 'Grande dialyse');
INSERT INTO public.unitedialyse VALUES (37, 'unité de dialyse é', 12, 'Grande dialyse');
INSERT INTO public.unitedialyse VALUES (41, 'Unite de dialyse 3', 20, 'Petite dialyse');




INSERT INTO public.utilisateur VALUES (1, 'zormad', '81dc9bdb52d04dc20036dbd8313ed055', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.utilisateur VALUES (2, 'admin', '81dc9bdb52d04dc20036dbd8313ed055', NULL, NULL, NULL, NULL, NULL, NULL, NULL);



SELECT pg_catalog.setval('public.branchement_idbranchement_seq', 18, true);



SELECT pg_catalog.setval('public.branchementsequence', 196952, true);



SELECT pg_catalog.setval('public.fonctionsequence', 43, true);



SELECT pg_catalog.setval('public.groupepatientsequuence', 1, false);



SELECT pg_catalog.setval('public.groupesequence', 28, true);



SELECT pg_catalog.setval('public.localitesequence', 9, true);



SELECT pg_catalog.setval('public.patientsequence', 48, true);



SELECT pg_catalog.setval('public.payssequence', 1, false);



SELECT pg_catalog.setval('public.postesequence', 8, true);



SELECT pg_catalog.setval('public.seancesequence', 55, true);



SELECT pg_catalog.setval('public.unitedialysesequence', 1, false);


ALTER TABLE ONLY public.branchement
    ADD CONSTRAINT branchement_pkey PRIMARY KEY (idbranchement);


ALTER TABLE ONLY public.fonction
    ADD CONSTRAINT pk_fonction PRIMARY KEY (idfonction);


ALTER TABLE ONLY public.groupe
    ADD CONSTRAINT pk_groupe PRIMARY KEY (idgroupe);


ALTER TABLE ONLY public.horaire
    ADD CONSTRAINT pk_horaire PRIMARY KEY (idhoraire);



ALTER TABLE ONLY public.jour
    ADD CONSTRAINT pk_jour PRIMARY KEY (idjour);


ALTER TABLE ONLY public.localite
    ADD CONSTRAINT pk_localite PRIMARY KEY (idlocalite);



ALTER TABLE ONLY public.patient
    ADD CONSTRAINT pk_patient PRIMARY KEY (idpatient);


ALTER TABLE ONLY public.pays
    ADD CONSTRAINT pk_pays PRIMARY KEY (idpays);



ALTER TABLE ONLY public.poste
    ADD CONSTRAINT pk_poste PRIMARY KEY (idposte);




ALTER TABLE ONLY public.seance
    ADD CONSTRAINT pk_seance PRIMARY KEY (idseance);


ALTER TABLE ONLY public.unitedialyse
    ADD CONSTRAINT pk_unitedialyse PRIMARY KEY (idunite);


ALTER TABLE ONLY public.profil
    ADD CONSTRAINT profil_pkey PRIMARY KEY (idprofil);



ALTER TABLE ONLY public.seancegroupe
    ADD CONSTRAINT seancegroupe_pkey PRIMARY KEY (idseance, idgroupe);



ALTER TABLE ONLY public.seancepatient
    ADD CONSTRAINT seancepatient_pkey PRIMARY KEY (idpatient, idseance);




ALTER TABLE ONLY public.utilisateur
    ADD CONSTRAINT utilisateur_pkey PRIMARY KEY (iduser);


CREATE INDEX association_10_fk ON public.seance USING btree (idjour);




CREATE INDEX association_11_fk ON public.branchement USING btree (idpatient);



CREATE INDEX association_12_fk ON public.branchement USING btree (idposte);



CREATE INDEX association_13_fk ON public.patient USING btree (idfonction);



CREATE INDEX association_2_fk ON public.patient USING btree (idlocalite);


CREATE INDEX association_3_fk ON public.poste USING btree (idunite);



CREATE INDEX association_6_fk ON public.branchement USING btree (idseance);


CREATE INDEX association_7_fk ON public.branchement USING btree (idhoraire);



CREATE INDEX association_9_fk ON public.localite USING btree (idpays);


CREATE UNIQUE INDEX fonction_pk ON public.fonction USING btree (idfonction);



CREATE UNIQUE INDEX groupe_pk ON public.groupe USING btree (idgroupe);


CREATE UNIQUE INDEX horaire_pk ON public.horaire USING btree (idhoraire);



CREATE UNIQUE INDEX jour_pk ON public.jour USING btree (idjour);


CREATE UNIQUE INDEX localite_pk ON public.localite USING btree (idlocalite);


CREATE UNIQUE INDEX patient_pk ON public.patient USING btree (idpatient);




CREATE UNIQUE INDEX pays_pk ON public.pays USING btree (idpays);



CREATE UNIQUE INDEX poste_pk ON public.poste USING btree (idposte);



CREATE UNIQUE INDEX seance_pk ON public.seance USING btree (idseance);



CREATE UNIQUE INDEX unitedialyse_pk ON public.unitedialyse USING btree (idunite);



ALTER TABLE ONLY public.branchement
    ADD CONSTRAINT fk_branchem_associati_horaire FOREIGN KEY (idhoraire) REFERENCES public.horaire(idhoraire) ON UPDATE RESTRICT ON DELETE RESTRICT;




ALTER TABLE ONLY public.branchement
    ADD CONSTRAINT fk_branchem_associati_patient FOREIGN KEY (idpatient) REFERENCES public.patient(idpatient) ON UPDATE RESTRICT ON DELETE RESTRICT;



ALTER TABLE ONLY public.branchement
    ADD CONSTRAINT fk_branchem_associati_poste FOREIGN KEY (idposte) REFERENCES public.poste(idposte) ON UPDATE RESTRICT ON DELETE RESTRICT;



ALTER TABLE ONLY public.branchement
    ADD CONSTRAINT fk_branchem_associati_seance FOREIGN KEY (idseance) REFERENCES public.seance(idseance) ON UPDATE RESTRICT ON DELETE RESTRICT;



ALTER TABLE ONLY public.localite
    ADD CONSTRAINT fk_localite_associati_pays FOREIGN KEY (idpays) REFERENCES public.pays(idpays) ON UPDATE RESTRICT ON DELETE RESTRICT;



ALTER TABLE ONLY public.patient
    ADD CONSTRAINT fk_patient_associati_fonction FOREIGN KEY (idfonction) REFERENCES public.fonction(idfonction) ON UPDATE RESTRICT ON DELETE RESTRICT;




ALTER TABLE ONLY public.patient
    ADD CONSTRAINT fk_patient_associati_localite FOREIGN KEY (idlocalite) REFERENCES public.localite(idlocalite) ON UPDATE RESTRICT ON DELETE RESTRICT;


ALTER TABLE ONLY public.patient
    ADD CONSTRAINT fk_patientseance FOREIGN KEY (idgroupe) REFERENCES public.groupe(idgroupe);




ALTER TABLE ONLY public.poste
    ADD CONSTRAINT fk_poste_associati_unitedia FOREIGN KEY (idunite) REFERENCES public.unitedialyse(idunite) ON UPDATE RESTRICT ON DELETE RESTRICT;



ALTER TABLE ONLY public.seance
    ADD CONSTRAINT fk_seance_associati_jour FOREIGN KEY (idjour) REFERENCES public.jour(idjour) ON UPDATE RESTRICT ON DELETE RESTRICT;



ALTER TABLE ONLY public.seance
    ADD CONSTRAINT fk_seance_unite FOREIGN KEY (idunite) REFERENCES public.unitedialyse(idunite);


ALTER TABLE ONLY public.utilisateur
    ADD CONSTRAINT fk_util_prof FOREIGN KEY (idprofil) REFERENCES public.profil(idprofil);



ALTER TABLE ONLY public.seancegroupe
    ADD CONSTRAINT seancegroupe_idgroupe_fkey FOREIGN KEY (idgroupe) REFERENCES public.groupe(idgroupe);



ALTER TABLE ONLY public.seancegroupe
    ADD CONSTRAINT seancegroupe_idseance_fkey FOREIGN KEY (idseance) REFERENCES public.seance(idseance);




ALTER TABLE ONLY public.seancepatient
    ADD CONSTRAINT seancepatient_idpatient_fkey FOREIGN KEY (idpatient) REFERENCES public.patient(idpatient);



ALTER TABLE ONLY public.seancepatient
    ADD CONSTRAINT seancepatient_idseance_fkey FOREIGN KEY (idseance) REFERENCES public.seance(idseance);


