/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import EspacoDetailComponent from '@/entities/espaco/espaco-details.vue';
import EspacoClass from '@/entities/espaco/espaco-details.component';
import EspacoService from '@/entities/espaco/espaco.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Espaco Management Detail Component', () => {
    let wrapper: Wrapper<EspacoClass>;
    let comp: EspacoClass;
    let espacoServiceStub: SinonStubbedInstance<EspacoService>;

    beforeEach(() => {
      espacoServiceStub = sinon.createStubInstance<EspacoService>(EspacoService);

      wrapper = shallowMount<EspacoClass>(EspacoDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { espacoService: () => espacoServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundEspaco = { id: 123 };
        espacoServiceStub.find.resolves(foundEspaco);

        // WHEN
        comp.retrieveEspaco(123);
        await comp.$nextTick();

        // THEN
        expect(comp.espaco).toBe(foundEspaco);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundEspaco = { id: 123 };
        espacoServiceStub.find.resolves(foundEspaco);

        // WHEN
        comp.beforeRouteEnter({ params: { espacoId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.espaco).toBe(foundEspaco);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
